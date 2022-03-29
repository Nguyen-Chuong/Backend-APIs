package com.capstone_project.hbts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RatingDTO {

    private int totalReview;

    private float averageService;

    private float averageValueForMoney;

    private float averageCleanliness;

    private float averageLocation;

    private float averageFacilities;

}
