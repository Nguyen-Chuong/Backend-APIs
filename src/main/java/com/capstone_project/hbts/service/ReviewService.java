package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.report.ReviewDTO;
import com.capstone_project.hbts.request.ReviewRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {

    /**
     * Load Review by hotelId
     */
    Page<ReviewDTO> loadReview(int hotelId, int page, int pageSize, int criteria);

    /**
     * For user to add new review about hotel
     */
    void addReview(ReviewRequest reviewRequest);

    /**
     * to check if user add a review or not
     */
    boolean isUserReviewAboutBooking(int bookingId);

    /**
     * Load total number review by hotelId
     */
    int totalReview(int hotelId);

    /**
     * Load total number review by hotelId
     */
    List<ReviewDTO> getTopReview(int hotelId, int limit);

}
