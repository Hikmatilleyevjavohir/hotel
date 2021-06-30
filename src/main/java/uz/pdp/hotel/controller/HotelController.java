package uz.pdp.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.Hotel;
import uz.pdp.hotel.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;
    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            return optionalHotel.get();
        }else {
            return new Hotel();
        }
    }

    @GetMapping
    public List<Hotel> getAllHotel(){
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        hotelRepository.save(hotel);
        return "Muvvafaqiyatli saqlandi ";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            Hotel hotel1 = optionalHotel.get();
            hotel1.setName(hotel.getName());
            hotelRepository.save(hotel1);
            return "o'zgartirlidi";
        }else {
            return "O'zgartirib bo'lmadi ";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()) {
            hotelRepository.delete(optionalHotel.get());
            return "O'chirildi";
        }else {
            return "o'chirib bo'lmadi ";
        }
    }
}
