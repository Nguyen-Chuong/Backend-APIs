package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Report.FeedbackDTO;
import com.capstone_project.hbts.request.FeedbackRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {

    /**
     * user send feedback to admin
     * @param feedbackRequest
     * @return
     */
    void sendFeedback(FeedbackRequest feedbackRequest);

    /**
     * admin view page user feedback
     * @param pageable
     * @return
     */
    Page<FeedbackDTO> viewPageUserFeedback(Pageable pageable);

    /**
     * admin view list an user's feedback
     * @param userId
     * @return
     */
    List<FeedbackDTO> getListAnUserFeedback(int userId);

    /**
     * get user's feedback by id
     * @param feedbackId
     * @return
     */
    FeedbackDTO getFeedbackById(int feedbackId);

    /**
     * update feedback receiver that click on
     * @param feedbackId
     * @param adminId
     * @return
     */
    void updateFeedbackReceiver(int feedbackId, int adminId);

}
