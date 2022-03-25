package com.capstone_project.hbts.service;

import com.capstone_project.hbts.request.FacilityAddRequest;

public interface FacilityService {

    /**
     * add list facility
     * @param facilityAddRequest
     * @param facilityTypeId
     */
    void addFacility(int facilityTypeId, FacilityAddRequest facilityAddRequest);

    /**
     * add a facility for provider that doesn't have in db
     * @param facilityName
     */
    void addFacilityOtherType(String facilityName);

}
