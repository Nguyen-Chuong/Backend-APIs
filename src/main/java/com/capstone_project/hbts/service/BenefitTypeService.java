package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.BenefitTypeDTO;
import com.capstone_project.hbts.request.BenefitTypeRequest;

import java.util.List;

public interface BenefitTypeService {

    /**
     * Get all benefit type
     */
    List<BenefitTypeDTO> getAllBenefitType();

    /**
     * Get list benefit by benefit type id
     */
    List<BenefitResult> getAllBenefitByTypeId(int benefitTypeId);

    /**
     * add benefit type
     */
    void addBenefitType(BenefitTypeRequest benefitTypeRequest);

}
