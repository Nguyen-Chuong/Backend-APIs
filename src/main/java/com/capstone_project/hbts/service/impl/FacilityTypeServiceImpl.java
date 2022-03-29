package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Facility.FacilityResult;
import com.capstone_project.hbts.dto.Facility.FacilityTypeDTO;
import com.capstone_project.hbts.entity.FacilityType;
import com.capstone_project.hbts.repository.FacilityTypeRepository;
import com.capstone_project.hbts.request.FacilityTypeRequest;
import com.capstone_project.hbts.service.FacilityTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityTypeServiceImpl implements FacilityTypeService {

    private final FacilityTypeRepository facilityTypeRepository;

    private final ModelMapper modelMapper;

    public FacilityTypeServiceImpl(FacilityTypeRepository facilityTypeRepository, ModelMapper modelMapper) {
        this.facilityTypeRepository = facilityTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FacilityTypeDTO> getAllFacilityType() {
        return facilityTypeRepository.findAll().stream().map(item -> modelMapper.map(item, FacilityTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FacilityResult> getAllFacilityByTypeId(int facilityTypeId) {
        return facilityTypeRepository.getFacilityTypeById(facilityTypeId).getListFacility().stream()
                .map(item -> modelMapper.map(item, FacilityResult.class)).collect(Collectors.toList());
    }

    @Override
    public void addFacilityType(FacilityTypeRequest facilityTypeRequest) {
        FacilityType facilityType = modelMapper.map(facilityTypeRequest, FacilityType.class);
        facilityTypeRepository.save(facilityType);
    }

}
