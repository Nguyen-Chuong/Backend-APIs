package com.capstone_project.hbts.dto.Hotel;

import com.capstone_project.hbts.dto.RatingDTO;
import com.capstone_project.hbts.dto.Room.RoomTypeDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HotelRatingDTO {

    private List<RoomTypeDTO> listRooms;

    private RatingDTO rating;

}
