package com.capstone_project.hbts.dto.Facility;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacilityDTO {

    private Integer id;

    private String name;

    private String icon;

    private FacilityTypeDTO facilityType;

}
