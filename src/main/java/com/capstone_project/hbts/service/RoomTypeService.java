package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomDetailDTO;
import com.capstone_project.hbts.dto.Room.RoomTypeDTO;
import com.capstone_project.hbts.request.RoomTypeRequest;

import java.util.Date;
import java.util.List;

public interface RoomTypeService {

    /**
     * Create room type for provider
     */
    void createRoomType(RoomTypeRequest roomTypeRequest);

    /**
     * get the room type id just insert
     */
    Integer getRoomIdJustInsert();

    /**
     * Update room type for provider
     */
    void updateRoomType(RoomTypeDTO roomTypeDTO);

    /**
     * Get room type by hotel id for guest and user
     */
    List<RoomTypeDTO> loadRoomTypeByHotelIdForSearch(int hotelId, Date dateIn, Date dateOut);

    /**
     * Disable room type
     */
    void disableRoomType(int roomTypeId);

    /**
     * Enable room type
     */
    void enableRoomType(int roomTypeId);

    /**
     * View detail of a room type
     */
    RoomDetailDTO viewRoomDetail(int roomTypeId);

    /**
     * Create sql event to update deal via date expired
     */
    void createSQLEventUpdateDealViaDateExpired();

    /**
     * Get room type by hotel id for admin and provider
     */
    List<RoomTypeDTO> loadRoomTypeByHotelId(int hotelId);

    /**
     * View detail of a room type with available room base on date in date out
     */
    RoomDetailDTO viewRoomDetailWithAvailableQuantity(int roomTypeId, Date dateIn, Date dateOut);

}
