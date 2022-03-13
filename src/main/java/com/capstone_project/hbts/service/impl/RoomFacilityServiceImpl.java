package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Room.RoomFacilityDTO;
import com.capstone_project.hbts.entity.Facility;
import com.capstone_project.hbts.entity.RoomFacility;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.repository.RoomFacilityRepository;
import com.capstone_project.hbts.request.FacilityRequest;
import com.capstone_project.hbts.service.RoomFacilityService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class RoomFacilityServiceImpl implements RoomFacilityService {

    private final RoomFacilityRepository roomFacilityRepository;

    private final ModelMapper modelMapper;

    public RoomFacilityServiceImpl(RoomFacilityRepository roomFacilityRepository, ModelMapper modelMapper) {
        this.roomFacilityRepository = roomFacilityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addListFacilityToRoomType(FacilityRequest facilityRequest) {
        log.info("Request to add list facility to room type");
        List<RoomFacility> roomFacilityList = new ArrayList<>();
        for(int i = 0 ; i < facilityRequest.getFacilityIds().size(); i++){
            // create a new room facility to save
            RoomFacility roomFacility = new RoomFacility();
            // create new room type and set id
            RoomType roomType = new RoomType();
            roomType.setId(facilityRequest.getRoomTypeId());
            // create new facility and set id
            Facility facility = new Facility();
            facility.setId(facilityRequest.getFacilityIds().get(i));
            // set prop for room facility
            roomFacility.setFacility(facility);
            roomFacility.setRoomType(roomType);
            // add them to list
            roomFacilityList.add(roomFacility);
        }
        // batch processing with max size 10
        roomFacilityRepository.saveAll(roomFacilityList);
    }

    @Override
    public List<RoomFacilityDTO> viewListFacility(int roomTypeId) {
        log.info("Request to view list room facility of a room type");
        return roomFacilityRepository.getAllByRoomTypeId(roomTypeId)
                .stream()
                .map(item -> modelMapper.map(item, RoomFacilityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoomFacility(int roomFacilityId) {
        log.info("Request to delete a facility of a room type");
        roomFacilityRepository.deleteById(roomFacilityId);
    }

    @Override
    public List<Integer> getListFacilityIds(int roomTypeId) {
        log.info("Request to get list facility id of a room type");
        return roomFacilityRepository.getListFacilityIds(roomTypeId);
    }

}
