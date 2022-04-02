package com.capstone_project.hbts.dto.hotel;

import com.capstone_project.hbts.dto.RatingDTO;
import com.capstone_project.hbts.dto.room.RoomTypeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class HotelRatingDTO {

    private List<RoomTypeDTO> listRooms;

    private RatingDTO rating;

}
