package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.report.ResponseDTO;
import com.capstone_project.hbts.request.ResponseAdminRequest;
import com.capstone_project.hbts.request.ResponseUserRequest;

import java.util.List;

public interface ResponseService {

    /**
     * admin send response to user
     */
    void sendResponseToUser(ResponseAdminRequest responseAdminRequest);

    /**
     * user send response to admin
     */
    void sendResponseToAdmin(ResponseUserRequest responseUserRequest);

    /**
     * get all response by feedback id
     */
    List<ResponseDTO> getAllResponseByFeedbackId(int feedbackId);

}
