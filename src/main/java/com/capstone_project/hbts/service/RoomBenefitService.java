package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Room.RoomBenefitDTO;
import com.capstone_project.hbts.request.BenefitRequest;

import java.util.List;

public interface RoomBenefitService {

    /**
     * Insert list benefit to a room type
     * @param benefitRequest
     */
    void addListBenefitToRoomType(BenefitRequest benefitRequest);

    /**
     * view list benefit of a room type
     * @param roomTypeId
     */
    List<RoomBenefitDTO> viewListBenefit(int roomTypeId);

    /**
     * for provider to delete their room benefit
     * @param roomBenefitId
     */
    void deleteRoomBenefit(int roomBenefitId);

    /**
     * get list benefit id of a room type
     * @param roomTypeId
     */
    List<Integer> getListBenefitIds(int roomTypeId);

    // have no option to update list benefit
    // if provider doesn't provide this benefit for this room anymore, they can delete it
    // if they want to change, add an other one

}
