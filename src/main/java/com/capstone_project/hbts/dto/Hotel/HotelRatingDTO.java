package com.capstone_project.hbts.dto.Hotel;

import com.capstone_project.hbts.dto.RatingDTO;
import com.capstone_project.hbts.dto.Room.RoomTypeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HotelRatingDTO {

    private List<RoomTypeDTO> listRooms;

    private RatingDTO rating;

}
