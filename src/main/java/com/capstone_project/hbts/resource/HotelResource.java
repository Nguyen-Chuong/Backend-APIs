package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.constant.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Hotel.HotelDTO;
import com.capstone_project.hbts.dto.Hotel.HotelDetailDTO;
import com.capstone_project.hbts.request.HotelRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.response.DataPagingResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.HotelServiceImpl;
import com.capstone_project.hbts.validation.ValidateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class HotelResource {

    private final HotelServiceImpl hotelService;

    private final JwtTokenUtil jwtTokenUtil;

    private final DataDecryption dataDecryption;

    public HotelResource(HotelServiceImpl hotelService, JwtTokenUtil jwtTokenUtil, DataDecryption dataDecryption) {
        this.hotelService = hotelService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.dataDecryption = dataDecryption;
    }

    @GetMapping("/public/search-hotel")
    public ResponseEntity<?> searchHotel(@RequestParam int districtId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateIn,
                                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOut, @RequestParam int numberOfPeople,
                                         @RequestParam int numberOfRoom, @RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                         @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize) {
        log.info("REST request to search hotel via district id and other info");
        if (!ValidateUtils.isFromDateBeforeToDate(dateIn, dateOut)) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_USER_009_LABEL));
        }
        try {
            Page<HotelDTO> hotelDTOPage = hotelService.searchHotel(districtId, dateIn, dateOut, numberOfPeople, numberOfRoom,
                    PageRequest.of(page, pageSize));
            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(hotelDTOPage.getContent(),
                    hotelDTOPage.getTotalElements(), page, hotelDTOPage.getSize());
            return ResponseEntity.ok().body(new ApiResponse<>(200, dataPagingResponse, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for admin/manager can view list all hotel in the system
     */
    @GetMapping("/get-hotel/{status}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAllHotel(@PathVariable int status, @RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                         @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize) {
        log.info("REST request to get all hotel by status for admin");
        try {
            Page<HotelDTO> hotelDTOPage = hotelService.getAllHotels(status, PageRequest.of(page, pageSize));
            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(hotelDTOPage.getContent(),
                    hotelDTOPage.getTotalElements(), page, hotelDTOPage.getSize());
            return ResponseEntity.ok().body(new ApiResponse<>(200, dataPagingResponse, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for admin/manager can view detail of a hotel
     */
    @GetMapping("/hotel-detail")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> viewHotelDetail(@RequestParam String hotelId) {
        log.info("REST request to get hotel detail by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            HotelDetailDTO hotelDetailDTO = hotelService.getDetailHotelById(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, hotelDetailDTO, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for admin can ban a hotel
     */
    @PatchMapping("/ban-hotel/{hotelId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> banHotelById(@PathVariable int hotelId) {
        log.info("REST request to ban hotel by hotel id");
        try {
            hotelService.banHotelByHotelId(hotelId);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can view list their hotel
     */
    @GetMapping("/list-hotel")
    public ResponseEntity<?> viewListHotelByProviderId(@RequestHeader("Authorization") String jwttoken) {
        log.info("REST request to get list hotel by provider id");
        int providerId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            List<HotelDTO> hotelDTOList = hotelService.viewListHotelByProviderId(providerId);
            return ResponseEntity.ok().body(new ApiResponse<>(200, hotelDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can disable a hotel, they can enable again if they want (is not banned)
     */
    @PatchMapping("/disable-hotel")
    public ResponseEntity<?> disableHotelById(@RequestParam String hotelId) {
        log.info("REST request to disable hotel by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            hotelService.disableHotel(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can enable a hotel again
     */
    @PatchMapping("/enable-hotel")
    public ResponseEntity<?> enableHotelById(@RequestParam String hotelId) {
        log.info("REST request to enable hotel by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        // if hotel status = 4 -> it is banned, provider cannot enable hotel again
        if (hotelService.viewHotelStatus(id) == 4) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_HOTEL_001_LABEL));
        }
        try {
            hotelService.enableHotel(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote get hotel by id
     */
    @GetMapping("/public/hotel")
    public ResponseEntity<?> viewHotelById(@RequestParam String hotelId) {
        log.info("REST request to get hotel by id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            HotelDTO hotelDTO = hotelService.getHotelById(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, hotelDTO, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider to add their hotel, they have to complete adding at least one room type to add a new request to post hotel
     */
    @PostMapping("/add-hotel")
    public ResponseEntity<?> addHotelForProvider(@RequestHeader("Authorization") String jwttoken, @RequestBody HotelRequest hotelRequest) {
        log.info("REST request to add new hotel for provider");
        int providerId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        // set provider id
        hotelRequest.setProviderId(providerId);
        try {
            hotelService.addHotelByProvider(hotelRequest);
            Integer hotelId = hotelService.getHotelIdJustInsert();
            return ResponseEntity.ok().body(new ApiResponse<>(200, hotelId, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for provider can update a hotel
     */
    @PatchMapping("/update-hotel-info")
    public ResponseEntity<?> updateHotel(@RequestBody HotelDTO hotelDTO) {
        log.info("REST request to update hotel");
        try {
            hotelService.updateHotel(hotelDTO);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote get top hot hotel
     */
    @GetMapping("/public/top-hotel")
    public ResponseEntity<?> viewTopHotelHot(@RequestParam String topHotel) {
        log.info("REST request to get top hot hotel");
        int top;
        try {
            top = dataDecryption.convertEncryptedDataToInt(topHotel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<HotelDTO> hotelDTOList = hotelService.getTopHotHotel(top);
            return ResponseEntity.ok().body(new ApiResponse<>(200, hotelDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
