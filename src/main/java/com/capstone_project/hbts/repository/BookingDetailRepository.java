package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.UserBookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BookingDetailRepository extends JpaRepository<UserBookingDetail, Integer> {

    // select when user click to view detail of a booking
    @Query(value = "SELECT * from capstone.user_booking_detail WHERE booking_id = :bookingId", nativeQuery = true)
    List<UserBookingDetail> getAllByBookingId(@Param("bookingId") int bookingId);

    @Query(value = "SELECT * from capstone.user_booking_detail WHERE room_type_id in :listRoomIds", nativeQuery = true)
    List<UserBookingDetail> getAllByRoomTypeId(@Param("listRoomIds") ArrayList<Integer> listRoomIds);

}
