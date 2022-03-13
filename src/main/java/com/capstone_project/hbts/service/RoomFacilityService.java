package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomFacilityDTO;
import com.capstone_project.hbts.request.FacilityRequest;

import java.util.List;

public interface RoomFacilityService {

    /**
     * Insert list facility to a room type
     * @param facilityRequest
     */
    void addListFacilityToRoomType(FacilityRequest facilityRequest);

    /**
     * view list facility of a room type
     * @param roomTypeId
     */
    List<RoomFacilityDTO> viewListFacility(int roomTypeId);

    /**
     * for provider to delete their room facility
     * @param roomFacilityId
     */
    void deleteRoomFacility(int roomFacilityId);

    /**
     * get list facility id of a room type
     * @param roomTypeId
     */
    List<Integer> getListFacilityIds(int roomTypeId);

    // have no option to update list facility
    // if provider doesn't provide this facility for this room anymore, they can delete it
    // if they want to change, add an other one

}
