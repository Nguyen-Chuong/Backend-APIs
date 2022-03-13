package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomDetailDTO;
import com.capstone_project.hbts.dto.Room.RoomTypeDTO;
import com.capstone_project.hbts.request.RoomTypeRequest;

import java.util.Date;
import java.util.List;

public interface RoomTypeService {

    /**
     * Create room type for provider
     * @param roomTypeRequest
     */
    void createRoomType(RoomTypeRequest roomTypeRequest);

    /**
     * get the room type id just insert
     * @param
     */
    Integer getRoomIdJustInsert();

    /**
     * Update room type for provider
     * @param roomTypeDTO
     */
    void updateRoomType(RoomTypeDTO roomTypeDTO);

    /**
     * Get room type by hotel id
     * @param hotelId
     */
    List<RoomTypeDTO> loadRoomTypeByHotelId(int hotelId, Date dateIn, Date dateOut);

    /**
     * Disable room type
     * @param roomTypeId
     */
    void disableRoomType(int roomTypeId);

    /**
     * Enable room type
     * @param roomTypeId
     */
    void enableRoomType(int roomTypeId);

    /**
     * View detail of a room type
     * @param roomTypeId
     */
    RoomDetailDTO viewRoomDetail(int roomTypeId);

    // have drop down list all to pick facility, benefit type -> filter list facility, benefit to pick
    // add to room facility, room benefit table repo

    /**
     * Create sql event to update deal via date expired
     * @param
     */
    void createSQLEventUpdateDealViaDateExpired();

}
