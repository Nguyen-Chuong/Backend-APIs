package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.benefit.BenefitResult;
import com.capstone_project.hbts.dto.benefit.BenefitTypeDTO;
import com.capstone_project.hbts.entity.BenefitType;
import com.capstone_project.hbts.repository.BenefitTypeRepository;
import com.capstone_project.hbts.request.BenefitTypeRequest;
import com.capstone_project.hbts.service.BenefitTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BenefitTypeServiceImpl implements BenefitTypeService {

    private final BenefitTypeRepository benefitTypeRepository;

    private final ModelMapper modelMapper;

    public BenefitTypeServiceImpl(BenefitTypeRepository benefitTypeRepository, ModelMapper modelMapper) {
        this.benefitTypeRepository = benefitTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BenefitTypeDTO> getAllBenefitType() {
        return benefitTypeRepository.findAll().stream().map(item -> modelMapper.map(item, BenefitTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BenefitResult> getAllBenefitByTypeId(int benefitTypeId) {
        return benefitTypeRepository.getBenefitTypeById(benefitTypeId).getListBenefit().stream()
                .map(item -> modelMapper.map(item, BenefitResult.class)).collect(Collectors.toList());
    }

    @Override
    public void addBenefitType(BenefitTypeRequest benefitTypeRequest) {
        BenefitType benefitType = modelMapper.map(benefitTypeRequest, BenefitType.class);
        benefitTypeRepository.save(benefitType);
    }

}
