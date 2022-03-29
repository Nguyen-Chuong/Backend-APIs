package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Report.ResponseDTO;
import com.capstone_project.hbts.repository.ResponseRepository;
import com.capstone_project.hbts.repository.UserRepository;
import com.capstone_project.hbts.request.ResponseAdminRequest;
import com.capstone_project.hbts.request.ResponseUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseServiceImpl{

    private final ResponseRepository responseRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public ResponseServiceImpl(ResponseRepository responseRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.responseRepository = responseRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // rule: two admins/managers cannot response to one user in one feedback (only one) and vice versa
    @Transactional
    public void sendResponseToUser(ResponseAdminRequest responseAdminRequest) {
        // get user id from username
        int userId = userRepository.getUserId(responseAdminRequest.getUsername());
        // get current timestamp
        responseAdminRequest.setModifyDate(new Timestamp(System.currentTimeMillis()));
        responseRepository.sendResponseFromFeedback(responseAdminRequest.getAdminId(), responseAdminRequest.getMessage(),
                responseAdminRequest.getModifyDate(), userId, responseAdminRequest.getFeedbackId());
    }

    @Transactional
    public void sendResponseToAdmin(ResponseUserRequest responseUserRequest) {
        // get admin id
        Integer adminId = responseRepository.getAdminId(responseUserRequest.getFeedbackId());
        // get current timestamp
        responseUserRequest.setModifyDate(new Timestamp(System.currentTimeMillis()));
        responseRepository.sendResponseFromFeedback(adminId, responseUserRequest.getMessage(), responseUserRequest.getModifyDate(),
                responseUserRequest.getUserId(), responseUserRequest.getFeedbackId());
    }

    public List<ResponseDTO> getAllResponseByFeedbackId(int feedbackId) {
        // first response always be admin/ manager
        return responseRepository.findAllByFeedback_IdOrderByModifyDateAsc(feedbackId).stream()
                .map(item -> modelMapper.map(item, ResponseDTO.class)).collect(Collectors.toList());
    }

}