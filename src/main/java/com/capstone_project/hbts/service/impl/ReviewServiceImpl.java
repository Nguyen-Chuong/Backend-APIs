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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
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
    public Page<ReviewDTO> loadReview(int hotelId, int page, int pageSize, int criteria) {
        // sort criteria
        Sort sort;
        if (criteria == 1) {
            sort = Sort.by("reviewDate").descending();
        } else {
            sort = Sort.by("total").descending();
        }
        Pageable pageable = PageRequest.of(page, pageSize, sort);
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
        for (int i = 0; i < reviewDTOList.size(); i++) {
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
        // count total average
        float total = (reviewRequest.getCleanliness() + reviewRequest.getService() + reviewRequest.getFacilities()
                + reviewRequest.getLocation() + reviewRequest.getValueForMoney()) / 5;
        reviewRequest.setTotal(total);
        // set time
        reviewRequest.setReviewDate(new Timestamp(System.currentTimeMillis()));
        Review review = modelMapper.map(reviewRequest, Review.class);
        // get user booking need to update review status
        UserBooking userBooking = bookingRepository.getBookingById(reviewRequest.getUserBookingId());
        review.setUserBooking(userBooking);
        // save review
        reviewRepository.save(review);
        // set status
        userBooking.setReviewStatus(1);
        // save it again
        bookingRepository.save(userBooking);
    }

    @Override
    public List<ReviewDTO> getTopReview(int hotelId, int limit) {
        // get list user booking
        List<UserBooking> userBookingList = bookingRepository.findUserBookingByHotelId(hotelId);
        // add list user booking ids to list
        ArrayList<Integer> listBookingId = new ArrayList<>();
        userBookingList.forEach(item -> listBookingId.add(item.getId()));
        List<Review> listReview = reviewRepository.loadReviewByBookingIdNoPaging(listBookingId);
        // sort by total score and limit result return
        List<Review> listSorted = listReview
                .stream()
                .sorted((Comparator.comparing(Review::getTotal).reversed()))
                .collect(Collectors.toList())
                .subList(0, limit);
        return listSorted.stream().map(item -> modelMapper.map(item, ReviewDTO.class)).collect(Collectors.toList());
    }

    @Override
    public boolean isUserReviewAboutBooking(int bookingId) {
        Review review = reviewRepository.loadOneReviewByUserBookingId(bookingId);
        return review != null;
    }

    @Override
    public int totalReview(int hotelId) {
        // get list user booking
        List<UserBooking> userBookingList = bookingRepository.findUserBookingByHotelId(hotelId);
        // add list user booking ids to list
        ArrayList<Integer> listBookingId = new ArrayList<>();
        userBookingList.forEach(item -> listBookingId.add(item.getId()));
        List<Review> listReview = reviewRepository.loadReviewByBookingIdNoPaging(listBookingId);
        return listReview.size();
    }

}
