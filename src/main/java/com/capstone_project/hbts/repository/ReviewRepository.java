package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query(value = "select r from Review r where r.userBooking.id in :userBookingIds")
    Page<Review> loadReviewByBookingId(@Param("userBookingIds") ArrayList<Integer> userBookingIds, Pageable pageable);

    @Query(value = "select r from Review r where r.userBooking.id in :userBookingIds")
    List<Review> loadReviewByBookingIdNoPaging(@Param("userBookingIds") ArrayList<Integer> userBookingIds);

    @Query(value = "select * from heroku_4fe5c149618a3f9.review where user_booking_id = :userBookingId limit 1", nativeQuery = true)
    Review loadOneReviewByUserBookingId(@Param("userBookingId") int userBookingId);

}
