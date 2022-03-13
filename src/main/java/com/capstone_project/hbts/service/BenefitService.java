package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;

import java.util.List;

public interface BenefitService {

    /**
     * Get all room's benefit in hotel
     * @param hotelId
     */
    List<ObjectBenefit> getListBenefitByHotelId(int hotelId);

}
