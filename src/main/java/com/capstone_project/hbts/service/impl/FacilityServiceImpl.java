package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Facility;
import com.capstone_project.hbts.entity.FacilityType;
import com.capstone_project.hbts.repository.FacilityRepository;
import com.capstone_project.hbts.request.FacilityAddRequest;
import com.capstone_project.hbts.request.FacilityRequest;
import com.capstone_project.hbts.service.FacilityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public void addFacility(int facilityTypeId, FacilityAddRequest facilityAddRequest) {
        List<Facility> facilityList = new ArrayList<>();
        for(FacilityRequest facilityRequest : facilityAddRequest.getListFacility()){
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
