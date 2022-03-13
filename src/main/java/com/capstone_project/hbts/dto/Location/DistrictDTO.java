package com.capstone_project.hbts.dto.Location;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DistrictDTO {

    private Integer id;

    private String nameDistrict;

    private CityDTO city;

}
