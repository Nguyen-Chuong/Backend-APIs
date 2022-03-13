package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Benefit.BenefitDTO;
import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.BenefitTypeDTO;
import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.repository.BenefitRepository;
import com.capstone_project.hbts.repository.HotelRepository;
import com.capstone_project.hbts.service.BenefitService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BenefitServiceImpl implements BenefitService {

    private final HotelRepository hotelRepository;

    private final ModelMapper modelMapper;

    private final BenefitRepository benefitRepository;

    public BenefitServiceImpl(HotelRepository hotelRepository, ModelMapper modelMapper,
                              BenefitRepository benefitRepository) {
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
        this.benefitRepository = benefitRepository;
    }

    @Override
    public List<ObjectBenefit> getListBenefitByHotelId(int hotelId) {
        log.info("Request to get all room's benefit by hotel id");
        // get list room type
        Set<RoomType> roomTypeSet = hotelRepository.getHotelById(hotelId).getListRoomType();
        // list benefit id from these room
        List<Integer> benefitId = new ArrayList<>();

        // add each item get benefitId into list benefit id
        for (RoomType roomType : roomTypeSet) {
            roomType.getListRoomBenefit().forEach(item -> benefitId.add(item.getBenefit().getId()));
        }
        // remove duplicate id from list id above
        List<Integer> benefitIdUnique = new ArrayList<>(new LinkedHashSet<>(benefitId));

        // get set benefitDTO by list id
        List<BenefitDTO> benefitDTOList = benefitRepository.findAllById(benefitIdUnique)
                .stream()
                .map(item -> modelMapper.map(item, BenefitDTO.class))
                .collect(Collectors.toList());

        // to remove duplicate benefit type
        Set<BenefitTypeDTO> setBenefitType = new HashSet<>();
        benefitDTOList.forEach(item -> setBenefitType.add(item.getBenefitType()));
        // list benefit to return
        List<ObjectBenefit> finalResultBenefit = new ArrayList<>();
        // loop through set benefit type
        for (BenefitTypeDTO item : setBenefitType) {
            // filter to add benefitDTOs that has this benefit type to a list
            List<BenefitDTO> listBenefit = benefitDTOList
                    .stream()
                    .filter(element -> element.getBenefitType().equals(item))
                    .collect(Collectors.toList());
            // remove BenefitTypeDTO property in list return
            List<BenefitResult> listBenefitResult = listBenefit
                    .stream()
                    .map(element -> modelMapper.map(element, BenefitResult.class))
                    .collect(Collectors.toList());
            // add new object benefit and put to list
            ObjectBenefit obj = new ObjectBenefit(item.getId(), item.getName(), item.getIcon(), listBenefitResult);
            finalResultBenefit.add(obj);
        }
        return finalResultBenefit;
    }

}
