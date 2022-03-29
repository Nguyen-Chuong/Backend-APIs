package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomFacilityDTO;
import com.capstone_project.hbts.request.RoomFacilityRequest;

import java.util.List;

public interface RoomFacilityService {

    /**
     * Insert list facility to a room type
     */
    void addListFacilityToRoomType(RoomFacilityRequest roomFacilityRequest);

    /**
     * view list facility of a room type
     */
    List<RoomFacilityDTO> viewListFacility(int roomTypeId);

    /**
     * for provider to delete their room facility
     */
    void deleteRoomFacility(int roomFacilityId);

    /**
     * get list facility id of a room type
     */
    List<Integer> getListFacilityIds(int roomTypeId);

}
