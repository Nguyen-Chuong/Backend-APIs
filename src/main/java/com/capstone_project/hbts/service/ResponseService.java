package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Report.ResponseDTO;
import com.capstone_project.hbts.request.ResponseAdminRequest;
import com.capstone_project.hbts.request.ResponseUserRequest;

import java.util.List;

public interface ResponseService {

    /**
     * admin send response to user
     * @param responseAdminRequest
     * @return
     */
    void sendResponseToUser(ResponseAdminRequest responseAdminRequest);

    /**
     * user send response to admin
     * @param responseUserRequest
     * @return
     */
    void sendResponseToAdmin(ResponseUserRequest responseUserRequest);

    /**
     * get all response by feedback id
     * @param feedbackId
     * @return
     */
    List<ResponseDTO> getAllResponseByFeedbackId(int feedbackId);

}
