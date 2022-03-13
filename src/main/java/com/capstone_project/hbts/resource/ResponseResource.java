package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Report.ResponseDTO;
import com.capstone_project.hbts.request.ResponseAdminRequest;
import com.capstone_project.hbts.request.ResponseUserRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.ResponseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("api/v1")
public class ResponseResource {

    private final ResponseService responseService;

    private final DataDecryption dataDecryption;

    public ResponseResource(ResponseService responseService, DataDecryption dataDecryption) {
        this.responseService = responseService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @param responseAdminRequest
     * return
     * @apiNote for manager/admin to send response
     * admin/manager cannot send response twice a time till user reply them
     * disable button reply
     */
    @PostMapping("/send-response/user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> sendResponseToUser(@RequestBody ResponseAdminRequest responseAdminRequest) {
        log.info("REST request to send response to user");

        try {
            responseService.sendResponseToUser(responseAdminRequest);
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
     * @param responseUserRequest
     * return
     * @apiNote for user to send response
     * if user click view feedback and see no response, disable button reply that they cannot call this api
     * or when they already clicked send response, they cannot send again till admin/manager reply
     */
    @PostMapping("/send-response/admin")
    public ResponseEntity<?> sendResponseToAdmin(@RequestBody ResponseUserRequest responseUserRequest) {
        log.info("REST request to send response to admin");

        try {
            responseService.sendResponseToAdmin(responseUserRequest);
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
     * @param feedbackId
     * return
     * @apiNote view list response for a feedback, both admin and user can use this api
     */
    @GetMapping("/view-response")
    public ResponseEntity<?> viewResponseByFeedbackId(@RequestParam String feedbackId) {
        log.info("REST request to get list response for feedback");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(feedbackId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        List<ResponseDTO> responseDTOList = responseService.getAllResponseByFeedbackId(id);
        try {
            if (responseDTOList.isEmpty()) {
                return ResponseEntity.ok()
                        .body(new ApiResponse<>(200, null,
                                ErrorConstant.ERR_USER_007, ErrorConstant.ERR_USER_007_LABEL));
            } else {
                return ResponseEntity.ok()
                        .body(new ApiResponse<>(200, responseDTOList,
                                null, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
