package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.report.FeedbackDTO;
import com.capstone_project.hbts.request.FeedbackRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {

    /**
     * user send feedback to admin
     */
    void sendFeedback(FeedbackRequest feedbackRequest);

    /**
     * admin view page user feedback
     */
    Page<FeedbackDTO> viewPageUserFeedback(Pageable pageable);

    /**
     * admin view list an user's feedback
     */
    List<FeedbackDTO> getListAnUserFeedback(int userId);

    /**
     * get user's feedback by id
     */
    FeedbackDTO getFeedbackById(int feedbackId);

    /**
     * update feedback receiver that click on
     */
    void updateFeedbackReceiver(int feedbackId, int adminId);

}
