package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.UserBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<UserBooking, Integer> {

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.user_booking WHERE user_id = :userId", nativeQuery = true)
    List<UserBooking> findAllByUserId(@Param("userId") int userId);

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.user_booking WHERE review_status = :reviewStatus " +
            "and user_id = :userId", nativeQuery = true)
    List<UserBooking> findBookingsReview(@Param("reviewStatus") int reviewStatus,
                                         @Param("userId") int userId);

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.user_booking WHERE hotel_id = :hotelId", nativeQuery = true)
    List<UserBooking> findUserBookingByHotelId(@Param("hotelId") int hotelId);

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.user_booking WHERE hotel_id = :hotelId and review_status = 1", nativeQuery = true)
    List<UserBooking> findUserBookingReviewedByHotelId(@Param("hotelId") int hotelId);

    // status user booking: cancelled, completed, when check vip status, only get number of booking that
    // have been completed, conditionally status = 1 for completed, may change later
    @Query(value = "SELECT count(id) from heroku_4fe5c149618a3f9.user_booking WHERE user_id = :userId and status = 1",
            nativeQuery = true)
    int numberBookingCompleted(@Param("userId") int userId);

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.user_booking WHERE status = :status and user_id = :userId",
            nativeQuery = true)
    List<UserBooking> findBookingsByStatus(@Param("status") int status,
                                           @Param("userId") int userId);

    Page<UserBooking> findAllByOrderByBookingDateDesc(Pageable pageable);

    Page<UserBooking> findAllByHotel_IdOrderByBookingDateDesc(int hotelId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.user_booking SET status = 3 WHERE id = :bookingId", nativeQuery = true)
    void cancelBooking(@Param("bookingId") int bookingId);

    @Query(value = "select * from heroku_4fe5c149618a3f9.user_booking where id = :id limit 1", nativeQuery = true)
    UserBooking getBookingById(@Param("id") int id);

    @Modifying
    @Query(value = "insert into heroku_4fe5c149618a3f9.user_booking(booked_quantity, booking_date, check_in, " +
            "check_out, review_status, status, hotel_id, user_id, other_requirement) " +
            "values (:bookedQuantity, :bookingDate, :checkIn, :checkOut, " +
            ":reviewStatus, :status, :hotelId, :userId, :otherRequirement)",
            nativeQuery = true)
    void addNewBooking(
            @Param("bookedQuantity") int bookedQuantity,
            @Param("bookingDate") Timestamp bookingDate,
            @Param("checkIn") Timestamp checkIn,
            @Param("checkOut") Timestamp checkOut,
            @Param("reviewStatus") int reviewStatus,
            @Param("status") int status,
            @Param("hotelId") int hotelId,
            @Param("userId") int userId,
            @Param("otherRequirement") String otherRequirement);

    @Query(value = "select last_insert_id(id) from heroku_4fe5c149618a3f9.user_booking order" +
            " by last_insert_id(id) desc limit 1;",
            nativeQuery = true)
    Integer getBookingIdJustInsert();

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.user_booking SET status = 2 WHERE id = :bookingId", nativeQuery = true)
    void completeBooking(@Param("bookingId") int bookingId);

}
