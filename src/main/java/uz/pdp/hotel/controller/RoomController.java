package uz.pdp.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.Hotel;
import uz.pdp.hotel.entity.Room;
import uz.pdp.hotel.payload.RoomDto;
import uz.pdp.hotel.repository.HotelRepository;
import uz.pdp.hotel.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;


    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("hotelId/{hotelId}")
    public Page<Room> getRoomByHotelId(@PathVariable Integer hotelId, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomRepositoryAllByHotelId = roomRepository.findAllByHotelId(hotelId, pageable);
        return roomRepositoryAllByHotelId;
    }

    @PostMapping()
    public String addRoom(@RequestBody RoomDto roomDto){
        boolean exists = roomRepository.existsByNumberAndFloorAndHotelId(roomDto.getNumber(), roomDto.getFloor(), roomDto.getHotelId());
        if (exists) {
            return "bunday xona mavjud";
        }
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent()) {
            return "Bunday Hotel mavjud emas";
        }
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "muvvafaqiyatli saqlandi";
    }

    @PutMapping("/{id}")
    public String editRoom(@RequestBody RoomDto roomDto, @PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setNumber(roomDto.getNumber());
            room.setFloor(roomDto.getFloor());
            room.setSize(roomDto.getSize());
            Hotel hotel = room.getHotel();
            hotel.setId(roomDto.getFloor());
            room.setHotel(hotel);
            roomRepository.save(room);
            return "o'zgartirildi";
        }else {
            return "o'zgartirib bo'lmadi ";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            roomRepository.delete(optionalRoom.get());
            return "o'chirildi";
        }else {
            return "O'chirib bo'lmadi ";
        }
    }
}
