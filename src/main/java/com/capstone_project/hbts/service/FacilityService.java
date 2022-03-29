package com.capstone_project.hbts.service;

import com.capstone_project.hbts.request.FacilityAddRequest;

public interface FacilityService {

    /**
     * add list facility
     */
    void addFacility(int facilityTypeId, FacilityAddRequest facilityAddRequest);

    /**
     * add a facility for provider that doesn't have in db
     */
    void addFacilityOtherType(String facilityName);

}
