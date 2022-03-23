package com.capstone_project.hbts.dto.Room;

import com.capstone_project.hbts.dto.Facility.FacilityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomFacilityDTO {

    private Integer id;

    private FacilityDTO facility;

}
