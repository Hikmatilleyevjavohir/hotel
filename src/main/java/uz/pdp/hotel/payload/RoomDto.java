package uz.pdp.hotel.payload;

import lombok.Data;
import uz.pdp.hotel.entity.Hotel;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Data
public class RoomDto {
    private Integer number;
    private Integer floor;
    private float size;
    private Integer hotelId;
}
