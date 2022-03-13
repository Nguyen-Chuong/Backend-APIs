package com.capstone_project.hbts.dto.Room;

import com.capstone_project.hbts.dto.Facility.FacilityDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomFacilityDTO {

    private Integer id;

    private FacilityDTO facility;

}
