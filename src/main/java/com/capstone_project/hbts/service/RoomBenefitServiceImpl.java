package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomBenefitDTO;
import com.capstone_project.hbts.entity.Benefit;
import com.capstone_project.hbts.entity.RoomBenefit;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.repository.RoomBenefitRepository;
import com.capstone_project.hbts.request.RoomBenefitRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomBenefitServiceImpl{

    private final RoomBenefitRepository roomBenefitRepository;

    private final ModelMapper modelMapper;

    public RoomBenefitServiceImpl(RoomBenefitRepository roomBenefitRepository, ModelMapper modelMapper) {
        this.roomBenefitRepository = roomBenefitRepository;
        this.modelMapper = modelMapper;
    }

    public void addListBenefitToRoomType(RoomBenefitRequest roomBenefitRequest) {
        List<RoomBenefit> roomBenefitList = new ArrayList<>();
        for(int i = 0; i < roomBenefitRequest.getBenefitIds().size(); i++){
            // create a new room benefit to save
            RoomBenefit roomBenefit = new RoomBenefit();
            // create new room type and set id
            RoomType roomType = new RoomType();
            roomType.setId(roomBenefitRequest.getRoomTypeId());
            // create new benefit and set id
            Benefit benefit = new Benefit();
            benefit.setId(roomBenefitRequest.getBenefitIds().get(i));
            // set prop for room benefit
            roomBenefit.setBenefit(benefit);
            roomBenefit.setRoomType(roomType);
            // add them to list
            roomBenefitList.add(roomBenefit);
        }
        // batch processing with max size 10
        roomBenefitRepository.saveAll(roomBenefitList);
    }

    public List<RoomBenefitDTO> viewListBenefit(int roomTypeId) {
        return roomBenefitRepository.getAllByRoomTypeId(roomTypeId).stream()
                .map(item -> modelMapper.map(item, RoomBenefitDTO.class)).collect(Collectors.toList());
    }

    public void deleteRoomBenefit(int roomBenefitId) {
        roomBenefitRepository.deleteById(roomBenefitId);
    }

    public List<Integer> getListBenefitIds(int roomTypeId) {
        return roomBenefitRepository.getListBenefitIds(roomTypeId);
    }

}
