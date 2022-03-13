package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Report.FeedbackDTO;
import com.capstone_project.hbts.entity.Feedback;
import com.capstone_project.hbts.repository.FeedbackRepository;
import com.capstone_project.hbts.request.FeedbackRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.FeedbackService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final ModelMapper modelMapper;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void sendFeedback(FeedbackRequest feedbackRequest) {
        log.info("Request to send an user feedback");
        // get current timestamp
        feedbackRequest.setModifyDate(new Timestamp(System.currentTimeMillis()));
        // not yet processed
        feedbackRequest.setIsProcessed(0);
        feedbackRepository.sendFeedback(feedbackRequest.getType(),
                feedbackRequest.getSenderId(),
                feedbackRequest.getMessage(),
                feedbackRequest.getModifyDate(),
                feedbackRequest.getIsProcessed());
    }

    @Override
    public Page<FeedbackDTO> viewPageUserFeedback(Pageable pageable) {
        log.info("Request to get all user feedback");

        Page<Feedback> feedbacks = feedbackRepository.findAllByOrderByModifyDateDesc(pageable);

        List<FeedbackDTO> feedbackDTOList = feedbacks.getContent()
                .stream()
                .map(item -> modelMapper.map(item, FeedbackDTO.class))
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
        log.info("Request to get list feedback of an user");

        List<Feedback> list = feedbackRepository.getUserFeedback(userId);

        List<FeedbackDTO> feedbackDTOList = list.stream()
                .map(item -> modelMapper.map(item, FeedbackDTO.class))
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
        log.info("Request to get feedback by id");
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

    @Override
    @Transactional
    public void updateFeedbackReceiver(int feedbackId, int adminId) {
        log.info("Request to update feedback receiver and process status");
        feedbackRepository.updateFeedbackReceiver(feedbackId, adminId);
    }

}
