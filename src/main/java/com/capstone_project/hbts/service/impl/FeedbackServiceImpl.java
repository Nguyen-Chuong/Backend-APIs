package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Report.FeedbackDTO;
import com.capstone_project.hbts.entity.Feedback;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.FeedbackRepository;
import com.capstone_project.hbts.request.FeedbackRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final ModelMapper modelMapper;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public void sendFeedback(FeedbackRequest feedbackRequest) {
        // get current timestamp
        feedbackRequest.setModifyDate(new Timestamp(System.currentTimeMillis()));
        // not yet processed
        feedbackRequest.setIsProcessed(0);
        // set sender
        Users users = new Users();
        users.setId(feedbackRequest.getSenderId());
        Feedback feedback = modelMapper.map(feedbackRequest, Feedback.class);
        feedback.setSender(users);
        // save feedback
        feedbackRepository.save(feedback);
    }

    @Override
    public Page<FeedbackDTO> viewPageUserFeedback(Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findAllByOrderByModifyDateDesc(pageable);
        List<FeedbackDTO> feedbackDTOList = feedbacks.getContent().stream().map(item -> modelMapper.map(item, FeedbackDTO.class))
                .collect(Collectors.toList());
        for (int i = 0; i < feedbackDTOList.size(); i++) {
            // set sender name
            feedbackDTOList.get(i).setSenderName(feedbacks.getContent().get(i).getSender().getUsername());
            // set receiver id
            if (feedbacks.getContent().get(i).getReceiver() == null) {
                feedbackDTOList.get(i).setReceiverId(0);
            } else {
                feedbackDTOList.get(i).setReceiverId(feedbacks.getContent().get(i).getReceiver().getId());
            }
        }
        return new CustomPageImpl<>(feedbackDTOList);
    }

    @Override
    public List<FeedbackDTO> getListAnUserFeedback(int userId) {
        List<Feedback> list = feedbackRepository.getUserFeedback(userId);
        List<FeedbackDTO> feedbackDTOList = list.stream().map(item -> modelMapper.map(item, FeedbackDTO.class))
                .collect(Collectors.toList());
        for (int i = 0; i < feedbackDTOList.size(); i++) {
            // set sender name
            feedbackDTOList.get(i).setSenderName(list.get(i).getSender().getUsername());
            // set receiver id
            if (list.get(i).getReceiver() == null) {
                feedbackDTOList.get(i).setReceiverId(0);
            } else {
                feedbackDTOList.get(i).setReceiverId(list.get(i).getReceiver().getId());
            }
        }
        return feedbackDTOList;

    }

    @Override
    public FeedbackDTO getFeedbackById(int feedbackId) {
        Feedback feedback = feedbackRepository.getFeedbackById(feedbackId);
        FeedbackDTO feedbackDTO = modelMapper.map(feedback, FeedbackDTO.class);
        // set sender name
        feedbackDTO.setSenderName(feedback.getSender().getUsername());
        // set receiver id
        if (feedback.getReceiver() == null) {
            feedbackDTO.setReceiverId(0);
        } else {
            feedbackDTO.setReceiverId(feedback.getReceiver().getId());
        }
        return feedbackDTO;
    }

    @Transactional
    @Override
    public void updateFeedbackReceiver(int feedbackId, int adminId) {
        feedbackRepository.updateFeedbackReceiver(feedbackId, adminId);
    }

}
