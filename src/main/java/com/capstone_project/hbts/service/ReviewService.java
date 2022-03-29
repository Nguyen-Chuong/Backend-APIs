package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Report.ReviewDTO;
import com.capstone_project.hbts.request.ReviewRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    /**
     * Load Review by hotelId
     */
    Page<ReviewDTO> loadReview(int hotelId, Pageable pageable);

    /**
     * For user to add new review about hotel
     */
    void addReview(ReviewRequest reviewRequest);

    /**
     * to check if user add a review or not
     */
    boolean isUserReviewAboutBooking(int bookingId);

}
