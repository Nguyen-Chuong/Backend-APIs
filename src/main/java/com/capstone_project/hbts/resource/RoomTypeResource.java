package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.hotel.HotelRatingDTO;
import com.capstone_project.hbts.dto.RatingDTO;
import com.capstone_project.hbts.dto.room.RoomDetailDTO;
import com.capstone_project.hbts.dto.room.RoomTypeDTO;
import com.capstone_project.hbts.request.RoomTypeRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.HotelService;
import com.capstone_project.hbts.service.RoomTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class RoomTypeResource {

    private final RoomTypeService roomTypeService;

    private final DataDecryption dataDecryption;

    private final HotelService hotelService;

    public RoomTypeResource(RoomTypeService roomTypeService, DataDecryption dataDecryption, HotelService hotelService) {
        this.roomTypeService = roomTypeService;
        this.dataDecryption = dataDecryption;
        this.hotelService = hotelService;
    }

    /**
     * @apiNote public for guest can view
     */
    @GetMapping("/public/room-type")
    public ResponseEntity<?> getListRoomTypeByHotelForSearch(@RequestParam String hotelId,
                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateIn,
                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOut) {
        log.info("REST request to get list room type by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<RoomTypeDTO> list = roomTypeService.loadRoomTypeByHotelIdForSearch(id, dateIn, dateOut);
            RatingDTO ratingDTO = hotelService.getRatingByHotel(id);
            // return hotel rating include rooms and average score user rating
            HotelRatingDTO hotelRatingDTO = new HotelRatingDTO();
            hotelRatingDTO.setListRooms(list);
            hotelRatingDTO.setRating(ratingDTO);
            return ResponseEntity.ok().body(new ApiResponse<>(200, hotelRatingDTO, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote public for guest can view
     */
    @GetMapping("/public/room-detail")
    public ResponseEntity<?> getRoomDetailByRoomTypeId(@RequestParam String roomTypeId) {
        log.info("REST request to get detail room type by room type id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            RoomDetailDTO roomDetailDTO = roomTypeService.viewRoomDetail(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, roomDetailDTO, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can add new room type for their hotel
     */
    @PostMapping("/add-room")
    public ResponseEntity<?> addRoomType(@RequestBody RoomTypeRequest roomTypeRequest) {
        log.info("REST request to add new room type for provider");
        try {
            roomTypeService.createRoomType(roomTypeRequest);
            Integer roomId = roomTypeService.getRoomIdJustInsert();
            return ResponseEntity.ok().body(new ApiResponse<>(200, roomId, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can update a room type for their hotel
     */
    @PatchMapping("/update-room")
    public ResponseEntity<?> updateRoomType(@RequestBody RoomTypeDTO roomTypeDTO) {
        log.info("REST request to update room type for provider");
        try {
            roomTypeService.updateRoomType(roomTypeDTO);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can disable a room type
     */
    @PatchMapping("/disable-room")
    public ResponseEntity<?> disableRoomType(@RequestParam String roomTypeId) {
        log.info("REST request to disable room type for provider");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            roomTypeService.disableRoomType(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can enable a room type
     */
    @PatchMapping("/enable-room")
    public ResponseEntity<?> enableRoomType(@RequestParam String roomTypeId) {
        log.info("REST request to enable room type for provider");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            roomTypeService.enableRoomType(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote create a sql event to auto update deal percentage when date expired
     */
    @PatchMapping("/create-event-update-deal")
    public ResponseEntity<?> createEventUpdateDeal() {
        log.info("REST request to create event update deal via date expired");
        try {
            roomTypeService.createSQLEventUpdateDealViaDateExpired();
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for admin or provider can view
     */
    @GetMapping("/list-room-type")
    public ResponseEntity<?> getListRoomTypeByHotel(@RequestParam String hotelId) {
        log.info("REST request to get list room type by hotel id for admin or provider");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<RoomTypeDTO> list = roomTypeService.loadRoomTypeByHotelId(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, list, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote public for guest can view
     */
    @GetMapping("/room-detail")
    public ResponseEntity<?> getRoomDetailAndAvailableQuantity(@RequestParam String roomTypeId,
                                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateIn,
                                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOut) {
        log.info("REST request to get detail room type by room type id and available quantity");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(roomTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            RoomDetailDTO roomDetailDTO = roomTypeService.viewRoomDetailWithAvailableQuantity(id, dateIn, dateOut);
            return ResponseEntity.ok().body(new ApiResponse<>(200, roomDetailDTO, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
