package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Facility.FacilityResult;
import com.capstone_project.hbts.dto.Facility.FacilityTypeDTO;

import java.util.List;

public interface FacilityTypeService {

    /**
     * Get all facility type
     * @param
     */
    List<FacilityTypeDTO> getAllFacilityType();

    /**
     * Get list benefit by benefit type id
     * @param facilityTypeId
     */
    List<FacilityResult> getAllFacilityByTypeId(int facilityTypeId);

}
