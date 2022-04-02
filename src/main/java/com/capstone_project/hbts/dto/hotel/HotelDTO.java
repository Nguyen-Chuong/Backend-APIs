package com.capstone_project.hbts.dto.hotel;

import com.capstone_project.hbts.dto.location.DistrictDTO;
import com.capstone_project.hbts.dto.RatingDTO;
import com.capstone_project.hbts.dto.report.ReviewDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class HotelDTO {

    private Integer id;

    private String name;

    private String address;

    private String avatar;

    private String description;

    private int status; // 1-active, 2-deactivated, 3-pending, 4-banned, 5-denied

    private String phone;

    private String email;

    private int star;

    private int taxPercentage;

    private DistrictDTO district;

    private long price;

    private int salePercent;

    private Timestamp dealExpired;

    private RatingDTO rating;

    private ReviewDTO review;

    private int totalHotel;

}
