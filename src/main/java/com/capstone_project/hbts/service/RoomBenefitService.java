package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomBenefitDTO;
import com.capstone_project.hbts.request.RoomBenefitRequest;

import java.util.List;

public interface RoomBenefitService {

    /**
     * Insert list benefit to a room type
     */
    void addListBenefitToRoomType(RoomBenefitRequest roomBenefitRequest);

    /**
     * view list benefit of a room type
     */
    List<RoomBenefitDTO> viewListBenefit(int roomTypeId);

    /**
     * for provider to delete their room benefit
     */
    void deleteRoomBenefit(int roomBenefitId);

    /**
     * get list benefit id of a room type
     */
    List<Integer> getListBenefitIds(int roomTypeId);

}
