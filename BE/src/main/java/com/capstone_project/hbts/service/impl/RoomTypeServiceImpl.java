package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Benefit.BenefitDTO;
import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.BenefitTypeDTO;
import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.dto.Facility.FacilityDTO;
import com.capstone_project.hbts.dto.Facility.FacilityResult;
import com.capstone_project.hbts.dto.Facility.FacilityTypeDTO;
import com.capstone_project.hbts.dto.Facility.ObjectFacility;
import com.capstone_project.hbts.dto.ImageDTO;
import com.capstone_project.hbts.dto.Room.RoomDetailDTO;
import com.capstone_project.hbts.dto.Room.RoomTypeDTO;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.repository.BenefitRepository;
import com.capstone_project.hbts.repository.FacilityRepository;
import com.capstone_project.hbts.repository.RoomTypeRepository;
import com.capstone_project.hbts.request.RoomTypeRequest;
import com.capstone_project.hbts.service.RoomTypeService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    private final ModelMapper modelMapper;

    private final FacilityRepository facilityRepository;

    private final BenefitRepository benefitRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository, ModelMapper modelMapper,
                               FacilityRepository facilityRepository, BenefitRepository benefitRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.modelMapper = modelMapper;
        this.facilityRepository = facilityRepository;
        this.benefitRepository = benefitRepository;
    }

    @Override
    @Transactional
    public void createRoomType(RoomTypeRequest roomTypeRequest) {
        log.info("Request to create room type");
        roomTypeRepository.addNewRoomType(roomTypeRequest.getAvailableRooms(),
                roomTypeRequest.getDealExpire(),
                roomTypeRequest.getDealPercentage(),
                roomTypeRequest.getName(),
                roomTypeRequest.getNumberOfPeople(),
                roomTypeRequest.getPrice(),
                roomTypeRequest.getQuantity(),
                roomTypeRequest.getHotelId());
    }

    @Override
    @Transactional
    public void updateRoomType(RoomTypeDTO roomTypeDTO) {
        log.info("Request to update room type");
        // get room to update
        RoomType roomType = roomTypeRepository.getRoomTypeById(roomTypeDTO.getId());
        roomType.setAvailableRooms(roomTypeDTO.getAvailableRooms());
        roomType.setDealExpire(roomTypeDTO.getDealExpire());
        roomType.setDealPercentage(roomTypeDTO.getDealPercentage());
        roomType.setName(roomTypeDTO.getName());
        roomType.setNumberOfPeople(roomTypeDTO.getNumberOfPeople());
        roomType.setPrice(roomTypeDTO.getPrice());
        roomType.setQuantity(roomTypeDTO.getQuantity());
        roomTypeRepository.save(roomType);
    }

    @Override
    public List<RoomTypeDTO> loadRoomTypeByHotelId(int hotelId) {
        log.info("Request to load room type by hotel id");

        List<RoomType> list = roomTypeRepository.findRoomTypeByHotelId(hotelId);

        return list.stream()
                .map(item -> modelMapper.map(item, RoomTypeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void disableRoomType(int roomTypeId) {
        log.info("Request to disable a room type");
        roomTypeRepository.disableRoomTypeById(roomTypeId);
    }

    @Override
    @Transactional
    public void enableRoomType(int roomTypeId) {
        log.info("Request to enable a room type");
        roomTypeRepository.enableRoomTypeById(roomTypeId);
    }

    @Override
    public RoomDetailDTO viewRoomDetail(int roomTypeId) {
        log.info("Request to view detail room type");
        // get room type by id
        RoomType roomType = roomTypeRepository.getRoomTypeById(roomTypeId);

        // get set image from this room type and transfer to DTO
        Set<ImageDTO> imageDTOSet = roomType.getListImage()
                .stream()
                .map(item -> modelMapper.map(item, ImageDTO.class))
                .collect(Collectors.toSet());

        // handle benefit
        List<Integer> listBenefitId = new ArrayList<>();
        // get benefit from this room and add id to list
        roomType.getListRoomBenefit().forEach(item -> listBenefitId.add(item.getBenefit().getId()));
        // get set benefitDTO by list id
        List<BenefitDTO> benefitDTOList = benefitRepository.findAllById(listBenefitId)
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

        // handle facility
        List<Integer> listFacilityId = new ArrayList<>();
        // get facility from this room and add id to list
        roomType.getListRoomFacility().forEach(item -> listFacilityId.add(item.getFacility().getId()));
        // get set FacilityDTO by list id
        List<FacilityDTO> facilityDTOList = facilityRepository.findAllById(listFacilityId)
                .stream()
                .map(item -> modelMapper.map(item, FacilityDTO.class))
                .collect(Collectors.toList());
        // to remove duplicate facility type
        Set<FacilityTypeDTO> setFacilityType = new HashSet<>();
        facilityDTOList.forEach(item -> setFacilityType.add(item.getFacilityType()));
        // list facility to return
        List<ObjectFacility> finalResultFacility = new ArrayList<>();
        // loop through set facility type
        for (FacilityTypeDTO item : setFacilityType) {
            // filter to add facilityDTOs that has this facility type to a list
            List<FacilityDTO> listFacility = facilityDTOList
                    .stream()
                    .filter(element -> element.getFacilityType().equals(item))
                    .collect(Collectors.toList());
            // remove FacilityTypeDTO property in list return
            List<FacilityResult> listFacilityResult = listFacility
                    .stream()
                    .map(element -> modelMapper.map(element, FacilityResult.class))
                    .collect(Collectors.toList());
            // add new object facility and put to list
            ObjectFacility obj = new ObjectFacility(item.getId(), item.getName(), item.getIcon(), listFacilityResult);
            finalResultFacility.add(obj);
        }

        // convert to RoomDetailDTO
        return new RoomDetailDTO(roomType.getId(),
                roomType.getName(),
                roomType.getPrice(),
                roomType.getNumberOfPeople(),
                roomType.getQuantity(),
                roomType.getAvailableRooms(),
                roomType.getDealPercentage(),
                roomType.getDealExpire(),
                imageDTOSet,
                finalResultFacility,
                finalResultBenefit);
    }

    @Override
    @Transactional
    public void createSQLEventUpdateDealViaDateExpired() {
        log.info("Request to create sql event update deal percentage");
        roomTypeRepository.createSQLEventUpdateDealViaDateExpired();
    }

}
