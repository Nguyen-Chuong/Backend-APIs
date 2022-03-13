package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query(value = "select * from capstone.review where user_booking_id in :userBookingIds", nativeQuery = true)
    Page<Review> loadReviewByBookingId(
            @Param("userBookingIds") ArrayList<Integer> userBookingIds,
            Pageable pageable);

    @Modifying
    @Query(value = "insert into capstone.review(cleanliness, facilities, location, " +
            "service, value_money, review_title, review_detail, user_booking_id, review_date) " +
            "values (:cleanliness, :facilities, :location, :service, :valueMoney, " +
            ":reviewTitle, :reviewDetail, :userBookingId, :reviewDate);",
            nativeQuery = true)
    void addNewReview(
            @Param("cleanliness") float cleanliness,
            @Param("facilities") float facilities,
            @Param("location") float location,
            @Param("service") float service,
            @Param("valueMoney") float valueMoney,
            @Param("reviewTitle") String reviewTitle,
            @Param("reviewDetail") String reviewDetail,
            @Param("userBookingId") int userBookingId,
            @Param("reviewDate") Timestamp reviewDate);

}
