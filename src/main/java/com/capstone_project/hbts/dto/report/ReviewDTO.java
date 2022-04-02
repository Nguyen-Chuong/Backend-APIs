package com.capstone_project.hbts.dto.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class ReviewDTO {

    private Integer id;

    private String username;

    private String avatar;

    private float service;

    private float valueForMoney;

    private String reviewTitle;

    private String reviewDetail;

    private float cleanliness;

    private float location;

    private float facilities;

    private Timestamp reviewDate;

    private float total;

}
