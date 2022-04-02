package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query(value = "select r from Review r where r.userBooking.id in :userBookingIds")
    Page<Review> loadReviewByBookingId(@Param("userBookingIds") ArrayList<Integer> userBookingIds, Pageable pageable);

    @Query(value = "select r from Review r where r.userBooking.id in :userBookingIds")
    List<Review> loadReviewByBookingIdNoPaging(@Param("userBookingIds") ArrayList<Integer> userBookingIds);

    @Modifying
    @Query(value = "insert into heroku_4fe5c149618a3f9.review(cleanliness, facilities, location, service, value_money, " +
            "review_title, review_detail, user_booking_id, review_date, total) values (:cleanliness, :facilities, :location, " +
            ":service, :valueMoney, :reviewTitle, :reviewDetail, :userBookingId, :reviewDate, :total)", nativeQuery = true)
    void addNewReview(@Param("cleanliness") float cleanliness, @Param("facilities") float facilities, @Param("location") float location,
                      @Param("service") float service, @Param("valueMoney") float valueMoney, @Param("reviewTitle") String reviewTitle,
                      @Param("reviewDetail") String reviewDetail, @Param("userBookingId") int userBookingId, @Param("total") float total,
                      @Param("reviewDate") Timestamp reviewDate);

    @Query(value = "select * from heroku_4fe5c149618a3f9.review where user_booking_id = :userBookingId limit 1", nativeQuery = true)
    Review loadOneReviewByUserBookingId(@Param("userBookingId") int userBookingId);

}
