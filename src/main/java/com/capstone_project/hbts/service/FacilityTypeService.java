package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Facility.FacilityResult;
import com.capstone_project.hbts.dto.Facility.FacilityTypeDTO;
import com.capstone_project.hbts.request.FacilityTypeRequest;

import java.util.List;

public interface FacilityTypeService {

    /**
     * Get all facility type
     */
    List<FacilityTypeDTO> getAllFacilityType();

    /**
     * Get list benefit by benefit type id
     */
    List<FacilityResult> getAllFacilityByTypeId(int facilityTypeId);

    /**
     * add facility type
     */
    void addFacilityType(FacilityTypeRequest facilityTypeRequest);

}
