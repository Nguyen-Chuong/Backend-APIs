package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.BenefitTypeDTO;

import java.util.List;

public interface BenefitTypeService {

    /**
     * Get all benefit type
     * @param
     */
    List<BenefitTypeDTO> getAllBenefitType();

    /**
     * Get list benefit by benefit type id
     * @param benefitTypeId
     */
    List<BenefitResult> getAllBenefitByTypeId(int benefitTypeId);

}
