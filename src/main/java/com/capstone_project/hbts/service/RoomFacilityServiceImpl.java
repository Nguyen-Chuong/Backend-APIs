package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomFacilityDTO;
import com.capstone_project.hbts.entity.Facility;
import com.capstone_project.hbts.entity.RoomFacility;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.repository.RoomFacilityRepository;
import com.capstone_project.hbts.request.RoomFacilityRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomFacilityServiceImpl {

    private final RoomFacilityRepository roomFacilityRepository;

    private final ModelMapper modelMapper;

    public RoomFacilityServiceImpl(RoomFacilityRepository roomFacilityRepository, ModelMapper modelMapper) {
        this.roomFacilityRepository = roomFacilityRepository;
        this.modelMapper = modelMapper;
    }

    public void addListFacilityToRoomType(RoomFacilityRequest roomFacilityRequest) {
        List<RoomFacility> roomFacilityList = new ArrayList<>();
        for(int i = 0; i < roomFacilityRequest.getFacilityIds().size(); i++){
            // create a new room facility to save
            RoomFacility roomFacility = new RoomFacility();
            // create new room type and set id
            RoomType roomType = new RoomType();
            roomType.setId(roomFacilityRequest.getRoomTypeId());
            // create new facility and set id
            Facility facility = new Facility();
            facility.setId(roomFacilityRequest.getFacilityIds().get(i));
            // set prop for room facility
            roomFacility.setFacility(facility);
            roomFacility.setRoomType(roomType);
            // add them to list
            roomFacilityList.add(roomFacility);
        }
        // batch processing with max size 10
        roomFacilityRepository.saveAll(roomFacilityList);
    }

    public List<RoomFacilityDTO> viewListFacility(int roomTypeId) {
        return roomFacilityRepository.getAllByRoomTypeId(roomTypeId).stream()
                .map(item -> modelMapper.map(item, RoomFacilityDTO.class)).collect(Collectors.toList());
    }

    public void deleteRoomFacility(int roomFacilityId) {
        roomFacilityRepository.deleteById(roomFacilityId);
    }

    public List<Integer> getListFacilityIds(int roomTypeId) {
        return roomFacilityRepository.getListFacilityIds(roomTypeId);
    }

}
