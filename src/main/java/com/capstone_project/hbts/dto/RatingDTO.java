package com.capstone_project.hbts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RatingDTO {

    private float averageService;

    private float averageValueForMoney;

    private float averageCleanliness;

    private float averageLocation;

    private float averageFacilities;

}
