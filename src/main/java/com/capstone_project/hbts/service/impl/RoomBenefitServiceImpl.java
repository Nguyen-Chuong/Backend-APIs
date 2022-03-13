package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Room.RoomBenefitDTO;
import com.capstone_project.hbts.entity.Benefit;
import com.capstone_project.hbts.entity.RoomBenefit;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.repository.RoomBenefitRepository;
import com.capstone_project.hbts.request.BenefitRequest;
import com.capstone_project.hbts.service.RoomBenefitService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RoomBenefitServiceImpl implements RoomBenefitService {

    private final RoomBenefitRepository roomBenefitRepository;

    private final ModelMapper modelMapper;

    public RoomBenefitServiceImpl(RoomBenefitRepository roomBenefitRepository, ModelMapper modelMapper) {
        this.roomBenefitRepository = roomBenefitRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addListBenefitToRoomType(BenefitRequest benefitRequest) {
        log.info("Request to add list benefit to room type");
        List<RoomBenefit> roomBenefitList = new ArrayList<>();
        for(int i = 0 ; i < benefitRequest.getBenefitIds().size(); i++){
            // create a new room benefit to save
            RoomBenefit roomBenefit = new RoomBenefit();
            // create new room type and set id
            RoomType roomType = new RoomType();
            roomType.setId(benefitRequest.getRoomTypeId());
            // create new benefit and set id
            Benefit benefit = new Benefit();
            benefit.setId(benefitRequest.getBenefitIds().get(i));
            // set prop for room benefit
            roomBenefit.setBenefit(benefit);
            roomBenefit.setRoomType(roomType);
            // add them to list
            roomBenefitList.add(roomBenefit);
        }
        // batch processing with max size 10
        roomBenefitRepository.saveAll(roomBenefitList);
    }

    @Override
    public List<RoomBenefitDTO> viewListBenefit(int roomTypeId) {
        log.info("Request to view list room benefit of a room type");
        return roomBenefitRepository.getAllByRoomTypeId(roomTypeId)
                .stream()
                .map(item -> modelMapper.map(item, RoomBenefitDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoomBenefit(int roomBenefitId) {
        log.info("Request to delete a room benefit of a room type");
        roomBenefitRepository.deleteById(roomBenefitId);
    }

    @Override
    public List<Integer> getListBenefitIds(int roomTypeId) {
        log.info("Request to get list benefit id of a room type");
        return roomBenefitRepository.getListBenefitIds(roomTypeId);
    }

}
