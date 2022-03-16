package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.request.BenefitRequest;

import java.util.List;

public interface BenefitService {

    /**
     * Get all room's benefit in hotel
     * @param hotelId
     */
    List<ObjectBenefit> getListBenefitByHotelId(int hotelId);

    /**
     * add list benefit
     * @param listBenefit
     * @param benefitTypeId
     */
    void addBenefit(int benefitTypeId, List<BenefitRequest> listBenefit);

}
