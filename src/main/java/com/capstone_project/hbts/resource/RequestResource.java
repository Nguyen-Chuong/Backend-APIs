package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.constants.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Request.RequestDTO;
import com.capstone_project.hbts.request.PostHotelRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.response.DataPagingResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.HotelService;
import com.capstone_project.hbts.service.RequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("api/v1")
public class RequestResource {

    private final RequestService requestService;

    private final JwtTokenUtil jwtTokenUtil;

    private final DataDecryption dataDecryption;

    private final HotelService hotelService;

    public RequestResource(RequestService requestService, JwtTokenUtil jwtTokenUtil,
                           DataDecryption dataDecryption, HotelService hotelService) {
        this.requestService = requestService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.dataDecryption = dataDecryption;
        this.hotelService = hotelService;
    }

    /**
     * @param jwttoken
     * @apiNote for provider to add their request to post hotel after completing
     * enough hotel information
     * @return
     */
    @PostMapping("/add-request")
    public ResponseEntity<?> addRequestPostHotel(@RequestHeader("Authorization") String jwttoken,
                                                 @RequestBody PostHotelRequest postHotelRequest){
        log.info("REST request to add new request to post hotel for provider");
        if(!hotelService.isHotelHadRoom(postHotelRequest.getHotelId())){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_HOTEL_002, ErrorConstant.ERR_HOTEL_002_LABEL));
        }
        // if hotel status = 4 -> it is banned, provider cannot send request for this hotel anymore
        if(hotelService.viewHotelStatus(postHotelRequest.getHotelId()) == 4){
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_HOTEL_001, ErrorConstant.ERR_HOTEL_001_LABEL));
        }
        int providerId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        // set provider id
        postHotelRequest.setProviderId(providerId);
        try{
            // if list request status contain 1 or 2 -> cannot request
            if(!requestService.checkRequest(postHotelRequest.getHotelId())){
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(400, null,
                                ErrorConstant.ERR_REQ_001, ErrorConstant.ERR_REQ_001_LABEL));
            } else { // else status 3-denied / 4-cancelled -> return 200 and can add request
                requestService.addNewRequest(postHotelRequest);
                return ResponseEntity.ok()
                        .body(new ApiResponse<>(200, null,
                                null, null));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param requestId
     * @apiNote for admin/manager to accept provider's request to post hotel
     * @return
     */
    @PatchMapping("/accept-request")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> acceptRequest(@RequestParam String requestId){
        log.info("REST request to accept provider's request to post hotel");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(requestId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try{
            requestService.acceptRequest(id);

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param requestId
     * @apiNote for admin/manager to deny provider's request to post hotel
     * @return
     */
    @PatchMapping("/deny-request")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> denyRequest(@RequestParam String requestId){
        log.info("REST request to deny provider's request to post hotel");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(requestId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try{
            requestService.denyRequest(id);

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param status
     * @apiNote for admin/manager to view list provider's request
     * @return
     */
    @GetMapping("/admin/view-request")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> viewListRequestByStatus(@RequestParam int status,
                                                     @RequestParam(defaultValue = ValidateConstant.PAGE) int page,
                                                     @RequestParam(defaultValue = ValidateConstant.PER_PAGE) int pageSize){
        log.info("REST request to view all provider's request by status");

        try{
            Page<RequestDTO> requestDTOPage = requestService.viewAllRequestByStatus(status,
                    PageRequest.of(page, pageSize));

            DataPagingResponse<?> dataPagingResponse = new DataPagingResponse<>(requestDTOPage.getContent(),
                    requestDTOPage.getTotalElements(), page, requestDTOPage.getSize());

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, dataPagingResponse,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param jwttoken
     * @apiNote for provider to view their list request
     * @return
     */
    @GetMapping("/provider/view-request")
    public ResponseEntity<?> viewListRequestByProviderId(@RequestHeader("Authorization") String jwttoken){
        log.info("REST request to view all provider's request by status");
        int providerId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));

        try{
            List<RequestDTO> requestDTOList = requestService.getRequestByProviderId(providerId);

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, requestDTOList,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param requestId
     * @apiNote for provider to cancel their request
     * they have to go to list request page to pick request to cancel
     * @return
     */
    @PatchMapping("/provider/cancel-request")
    public ResponseEntity<?> cancelRequest(@RequestParam String requestId){
        log.info("REST request to cancel request by provider");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(requestId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try{
            if(requestService.getRequestStatus(id) != 1){
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(400, null,
                                ErrorConstant.ERR_REQ_002, ErrorConstant.ERR_REQ_002_LABEL));
            } else {
                requestService.cancelRequest(id);
                return ResponseEntity.ok()
                        .body(new ApiResponse<>(200, null,
                                null, null));
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
