package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Room.RoomFacilityDTO;
import com.capstone_project.hbts.request.FacilityRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.RoomFacilityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
@Log4j2
@RequestMapping("api/v1")
public class RoomFacilityResource {

    private final RoomFacilityService roomFacilityService;

    private final DataDecryption dataDecryption;

    public RoomFacilityResource(RoomFacilityService roomFacilityService, DataDecryption dataDecryption) {
        this.roomFacilityService = roomFacilityService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @param facilityRequest
     * @apiNote for provider can add a list facility to their room type
     * return
     */
    @PostMapping("/add-list-facility")
    public ResponseEntity<?> addListRoomFacility(@RequestBody FacilityRequest facilityRequest) {
        log.info("REST request to add a list facility to provider's room type");
        // get all facility ids from db
        List<Integer> listFacilityIds = roomFacilityService.getListFacilityIds(facilityRequest.getRoomTypeId());
        // list all ids from user request
        List<Integer> listIds = facilityRequest.getFacilityIds();
        // check list common items in two lists (if have)
        List<Integer> common = new ArrayList<>(listFacilityIds);
        common.retainAll(listIds);
        // check if they have common item
        if(!common.isEmpty()){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_ITEM_002, ErrorConstant.ERR_ITEM_002_LABEL));
        }
        Set<Integer> setIds = new HashSet<>(listIds);
        if(setIds.size() < listIds.size()){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_ITEM_001, ErrorConstant.ERR_ITEM_001_LABEL));
        }
        try {
            roomFacilityService.addListFacilityToRoomType(facilityRequest);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param roomTypeId
     * @apiNote for provider can view a list facility of their room
     * return
     */
    @GetMapping("/get-room-facility")
    public ResponseEntity<?> getListRoomFacility(@RequestParam String roomTypeId) {
        log.info("REST request to view a list facility of room type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<RoomFacilityDTO> roomFacilityDTOList= roomFacilityService.viewListFacility(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, roomFacilityDTOList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param roomFacilityId
     * @apiNote for provider can delete a facility of their room
     * return
     */
    @DeleteMapping("/delete-room-facility")
    public ResponseEntity<?> deleteRoomFacility(@RequestParam String roomFacilityId) {
        log.info("REST request to delete a facility of room type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomFacilityId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            roomFacilityService.deleteRoomFacility(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
