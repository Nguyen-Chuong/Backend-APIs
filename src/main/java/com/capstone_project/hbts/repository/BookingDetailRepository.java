package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.UserBookingDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BookingDetailRepository extends CrudRepository<UserBookingDetail, Integer> {

    // select when user click to view detail of a booking
    @Query(value = "SELECT u from UserBookingDetail u WHERE u.userBooking.id = :bookingId")
    List<UserBookingDetail> getAllByBookingId(@Param("bookingId") int bookingId);

    @Query(value = "SELECT u from UserBookingDetail u WHERE u.roomType.id in :listRoomIds")
    List<UserBookingDetail> getAllByRoomTypeId(@Param("listRoomIds") ArrayList<Integer> listRoomIds);

}
