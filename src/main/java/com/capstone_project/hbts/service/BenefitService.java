package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.request.BenefitAddRequest;

import java.util.List;

public interface BenefitService {

    /**
     * Get all room's benefit in hotel
     * @param hotelId
     */
    List<ObjectBenefit> getListBenefitByHotelId(int hotelId);

    /**
     * add list benefit
     * @param benefitAddRequest
     * @param benefitTypeId
     */
    void addBenefit(int benefitTypeId, BenefitAddRequest benefitAddRequest);

    /**
     * add a benefit for provider that doesn't have in db
     * @param benefitName
     */
    void addBenefitOtherType(String benefitName);

}
