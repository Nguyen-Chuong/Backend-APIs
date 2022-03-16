package com.capstone_project.hbts.service;

import com.capstone_project.hbts.request.FacilityRequest;

import java.util.List;

public interface FacilityService {

    /**
     * add list facility
     * @param listFacility
     * @param facilityTypeId
     */
    void addFacility(int facilityTypeId, List<FacilityRequest> listFacility);

    /**
     * add a facility for provider that doesn't have in db
     * @param facilityName
     */
    void addFacilityOtherType(String facilityName);

}
