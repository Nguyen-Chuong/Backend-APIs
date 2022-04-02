package com.capstone_project.hbts.dto.facility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FacilityDTO {

    private Integer id;

    private String name;

    private String icon;

    private FacilityTypeDTO facilityType;

}
