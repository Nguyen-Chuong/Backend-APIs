package com.capstone_project.hbts.dto.location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class DistrictDTO {

    private Integer id;

    private String nameDistrict;

    private String avatar;

    private long totalBooking;

    private CityDTO city;

}
