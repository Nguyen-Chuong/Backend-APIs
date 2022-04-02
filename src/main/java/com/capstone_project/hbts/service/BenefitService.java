package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.benefit.ObjectBenefit;
import com.capstone_project.hbts.request.BenefitAddRequest;

import java.util.List;

public interface BenefitService {

    /**
     * Get all room's benefit in hotel
     */
    List<ObjectBenefit> getListBenefitByHotelId(int hotelId);

    /**
     * add list benefit
     */
    void addBenefit(int benefitTypeId, BenefitAddRequest benefitAddRequest);

    /**
     * add a benefit for provider that doesn't have in db
     */
    void addBenefitOtherType(String benefitName);

}
