package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Report.ReviewDTO;
import com.capstone_project.hbts.entity.Review;
import com.capstone_project.hbts.entity.UserBooking;
import com.capstone_project.hbts.repository.BookingRepository;
import com.capstone_project.hbts.repository.ReviewRepository;
import com.capstone_project.hbts.request.ReviewRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final BookingRepository bookingRepository;

    private final ReviewRepository reviewRepository;

    private final ModelMapper modelMapper;

    public ReviewServiceImpl(BookingRepository bookingRepository, ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<ReviewDTO> loadReview(int hotelId, Pageable pageable) {
        // get list user booking
        List<UserBooking> userBookingList = bookingRepository.findUserBookingByHotelId(hotelId);
        // add list user booking ids to list
        ArrayList<Integer> listBookingId = new ArrayList<>();
        userBookingList.forEach(item -> listBookingId.add(item.getId()));
        // get page review by list user booking id
        Page<Review> pageReview = reviewRepository.loadReviewByBookingId(listBookingId, pageable);
        List<Review> listReview = new ArrayList<>(pageReview.getContent());
        List<ReviewDTO> reviewDTOList = listReview.stream().map(item -> modelMapper.map(item, ReviewDTO.class))
                .collect(Collectors.toList());
        for(int i = 0; i < reviewDTOList.size(); i++){
            // set user name for review
            reviewDTOList.get(i).setUsername(listReview.get(i).getUserBooking().getUsers().getUsername().substring(2));
            // set avatar for user review
            reviewDTOList.get(i).setAvatar(listReview.get(i).getUserBooking().getUsers().getAvatar());
        }
        return new CustomPageImpl<>(reviewDTOList);
    }

    @Transactional
    @Override
    public void addReview(ReviewRequest reviewRequest) {
        reviewRepository.addNewReview(reviewRequest.getCleanliness(), reviewRequest.getFacilities(), reviewRequest.getLocation(),
                reviewRequest.getService(), reviewRequest.getValueForMoney(), reviewRequest.getReviewTitle(),
                reviewRequest.getReviewDetail(), reviewRequest.getUserBookingId(), new Timestamp(System.currentTimeMillis()));
        // get user booking need to update review status
        UserBooking userBooking = bookingRepository.getBookingById(reviewRequest.getUserBookingId());
        // set status
        userBooking.setReviewStatus(1);
        // save it again
        bookingRepository.save(userBooking);
    }

    @Override
    public boolean isUserReviewAboutBooking(int bookingId) {
        Review review = reviewRepository.loadOneReviewByUserBookingId(bookingId);
        return review != null;
    }

}