package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Room.RoomDetailDTO;
import com.capstone_project.hbts.dto.Room.RoomTypeDTO;
import com.capstone_project.hbts.request.RoomTypeRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.RoomTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("api/v1")
public class RoomTypeResource {

    private final RoomTypeService roomTypeService;

    private final DataDecryption dataDecryption;

    public RoomTypeResource(RoomTypeService roomTypeService, DataDecryption dataDecryption) {
        this.roomTypeService = roomTypeService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @param hotelId
     * @apiNote public for guest can view
     * return
     */
    @GetMapping("/public/room-type")
    public ResponseEntity<?> getListRoomTypeByHotel(@RequestParam String hotelId,
                                                    @RequestParam(required = false) @DateTimeFormat
                                                            (pattern = "yyyy-MM-dd") Date dateIn,
                                                    @RequestParam(required = false) @DateTimeFormat
                                                            (pattern = "yyyy-MM-dd") Date dateOut) {
        log.info("REST request to get list room type by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<RoomTypeDTO> list = roomTypeService.loadRoomTypeByHotelId(id, dateIn, dateOut);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, list,
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
     * @apiNote public for guest can view
     * return
     */
    @GetMapping("/public/room-detail")
    public ResponseEntity<?> getRoomDetailByRoomTypeId(@RequestParam String roomTypeId) {
        log.info("REST request to get detail room type by room type id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            RoomDetailDTO roomDetailDTO = roomTypeService.viewRoomDetail(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, roomDetailDTO,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param roomTypeRequest
     * @apiNote for provider can add new room type for their hotel
     * return
     */
    @PostMapping("/add-room")
    public ResponseEntity<?> addRoomType(@RequestBody RoomTypeRequest roomTypeRequest) {
        log.info("REST request to add new room type for provider");

        try {
            roomTypeService.createRoomType(roomTypeRequest);
            Integer roomId = roomTypeService.getRoomIdJustInsert();

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, roomId,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param roomTypeDTO
     * @apiNote for provider can update a room type for their hotel
     * return
     */
    @PatchMapping("/update-room")
    public ResponseEntity<?> updateRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        log.info("REST request to update room type for provider");

        try {
            roomTypeService.updateRoomType(roomTypeDTO);
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
     * @apiNote for provider can disable a room type
     * return
     */
    @PatchMapping("/disable-room")
    public ResponseEntity<?> disableRoomType(@RequestParam String roomTypeId) {
        log.info("REST request to disable room type for provider");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            roomTypeService.disableRoomType(id);
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
     * @apiNote for provider can enable a room type
     * return
     */
    @PatchMapping("/enable-room")
    public ResponseEntity<?> enableRoomType(@RequestParam String roomTypeId) {
        log.info("REST request to enable room type for provider");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            roomTypeService.enableRoomType(id);
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
     * @param
     * @apiNote create a sql event to auto update deal percentage via date expired
     * return
     */
    @PatchMapping("/create-event-update-deal")
    public ResponseEntity<?> createEventUpdateDeal() {
        log.info("REST request to create event update deal via date expired");

        try {
            roomTypeService.createSQLEventUpdateDealViaDateExpired();
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
