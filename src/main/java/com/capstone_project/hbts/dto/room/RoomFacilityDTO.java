package com.capstone_project.hbts.dto.room;

import com.capstone_project.hbts.dto.facility.FacilityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RoomFacilityDTO {

    private Integer id;

    private FacilityDTO facility;

}
