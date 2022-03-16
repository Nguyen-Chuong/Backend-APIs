package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Facility;
import com.capstone_project.hbts.entity.FacilityType;
import com.capstone_project.hbts.repository.FacilityRepository;
import com.capstone_project.hbts.request.FacilityRequest;
import com.capstone_project.hbts.service.FacilityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public void addFacility(int facilityTypeId, List<FacilityRequest> listFacility) {
        log.info("Request to add a list facility for admin");
        List<Facility> facilityList = new ArrayList<>();
        for(FacilityRequest facilityRequest : listFacility){
            Facility facility = new Facility();
            facility.setName(facilityRequest.getName());
            facility.setIcon(facilityRequest.getIcon());
            // set benefit type
            FacilityType facilityType = new FacilityType();
            facilityType.setId(facilityTypeId);
            facility.setFacilityType(facilityType);
            // add them to list
            facilityList.add(facility);
        }
        // batch processing
        facilityRepository.saveAll(facilityList);
    }

    @Override
    public void addFacilityOtherType(String facilityName) {
        log.info("Request to add a facility for provider that doesn't have in db");
        Facility facility = new Facility();
        // set name
        facility.setName(facilityName);
        FacilityType facilityType = new FacilityType();
        facilityType.setId(1);
        // set type other
        facility.setFacilityType(facilityType);
        // save
        facilityRepository.save(facility);
    }

}
