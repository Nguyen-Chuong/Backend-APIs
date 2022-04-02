package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Report.ResponseDTO;
import com.capstone_project.hbts.repository.ResponseRepository;
import com.capstone_project.hbts.repository.UserRepository;
import com.capstone_project.hbts.request.ResponseAdminRequest;
import com.capstone_project.hbts.request.ResponseUserRequest;
import com.capstone_project.hbts.service.ResponseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseServiceImpl implements ResponseService {

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
    @Override
    public void sendResponseToUser(ResponseAdminRequest responseAdminRequest) {
        // get user id from username
        int userId = userRepository.getUserId(responseAdminRequest.getUsername());
        // get current timestamp
        responseAdminRequest.setModifyDate(new Timestamp(System.currentTimeMillis()));
        // set send by
        responseAdminRequest.setSendBy(0);
        responseRepository.sendResponseFromFeedback(responseAdminRequest.getAdminId(), responseAdminRequest.getMessage(),
                responseAdminRequest.getSendBy(), responseAdminRequest.getModifyDate(), userId, responseAdminRequest.getFeedbackId());
    }

    @Transactional
    @Override
    public void sendResponseToAdmin(ResponseUserRequest responseUserRequest) {
        // get admin id
        int adminId = responseRepository.getAdminId(responseUserRequest.getFeedbackId());
        // get current timestamp
        responseUserRequest.setModifyDate(new Timestamp(System.currentTimeMillis()));
        // set send by
        responseUserRequest.setSendBy(1);
        responseRepository.sendResponseFromFeedback(adminId, responseUserRequest.getMessage(), responseUserRequest.getSendBy(),
                responseUserRequest.getModifyDate(), responseUserRequest.getUserId(), responseUserRequest.getFeedbackId());
    }

    @Override
    public List<ResponseDTO> getAllResponseByFeedbackId(int feedbackId) {
        // first response always be admin/ manager
        return responseRepository.findAllByFeedback_IdOrderByModifyDateAsc(feedbackId).stream()
                .map(item -> modelMapper.map(item, ResponseDTO.class)).collect(Collectors.toList());
    }

}
