package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.BenefitTypeDTO;
import com.capstone_project.hbts.repository.BenefitTypeRepository;
import com.capstone_project.hbts.service.BenefitTypeService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BenefitTypeServiceImpl implements BenefitTypeService {

    private final BenefitTypeRepository benefitTypeRepository;

    private final ModelMapper modelMapper;

    public BenefitTypeServiceImpl(BenefitTypeRepository benefitTypeRepository, ModelMapper modelMapper) {
        this.benefitTypeRepository = benefitTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BenefitTypeDTO> getAllBenefitType() {
        log.info("Request to get all benefit type");
        return benefitTypeRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, BenefitTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<BenefitResult> getAllBenefitByTypeId(int benefitTypeId) {
        log.info("Request to get all benefit by benefitType");
        return benefitTypeRepository.getBenefitTypeById(benefitTypeId)
                .getListBenefit()
                .stream()
                .map(item -> modelMapper.map(item, BenefitResult.class))
                .collect(Collectors.toList());
    }

}
