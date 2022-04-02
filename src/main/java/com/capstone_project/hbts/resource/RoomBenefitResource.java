package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.room.RoomBenefitDTO;
import com.capstone_project.hbts.request.RoomBenefitRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.RoomBenefitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class RoomBenefitResource {

    private final RoomBenefitService roomBenefitService;

    private final DataDecryption dataDecryption;

    public RoomBenefitResource(RoomBenefitService roomBenefitService, DataDecryption dataDecryption) {
        this.roomBenefitService = roomBenefitService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @apiNote for provider can add a list benefit to their room type
     */
    @PostMapping("/add-list-benefit")
    public ResponseEntity<?> addListRoomBenefit(@RequestBody RoomBenefitRequest roomBenefitRequest) {
        log.info("REST request to add a list benefit to provider's room type");
        // get all benefit ids from db
        List<Integer> listBenefitIds = roomBenefitService.getListBenefitIds(roomBenefitRequest.getRoomTypeId());
        // list all ids from user request
        List<Integer> listIds = roomBenefitRequest.getBenefitIds();
        // check list common items in two lists (if have)
        List<Integer> common = new ArrayList<>(listBenefitIds);
        common.retainAll(listIds);
        // check if they have common item
        if (!common.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_ITEM_002_LABEL));
        }
        Set<Integer> setIds = new HashSet<>(listIds);
        if (setIds.size() < listIds.size()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_ITEM_001_LABEL));
        }
        try {
            roomBenefitService.addListBenefitToRoomType(roomBenefitRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can view a list benefit of their room
     */
    @GetMapping("/get-room-benefit")
    public ResponseEntity<?> getListRoomBenefit(@RequestParam String roomTypeId) {
        log.info("REST request to view a list benefit of room type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<RoomBenefitDTO> roomBenefitDTOList = roomBenefitService.viewListBenefit(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, roomBenefitDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can delete a benefit of their room
     */
    @DeleteMapping("/delete-room-benefit")
    public ResponseEntity<?> deleteRoomBenefit(@RequestParam String roomBenefitId) {
        log.info("REST request to delete a benefit of room type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomBenefitId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            roomBenefitService.deleteRoomBenefit(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
