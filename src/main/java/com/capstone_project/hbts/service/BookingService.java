package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.booking.BookingListDTO;
import com.capstone_project.hbts.dto.booking.UserBookingDTO;
import com.capstone_project.hbts.request.BookingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    /**
     * get all bookings
     */
    List<UserBookingDTO> getAllBookings(int userId);

    /**
     * get all bookings need to review or done
     */
    List<UserBookingDTO> getAllBookingsReview(int reviewStatus, int userId);

    /**
     * count number of all bookings completed by user id
     */
    int getNumberBookingsCompleted(int userId);

    /**
     * get all bookings by status
     */
    List<UserBookingDTO> getAllBookingsByStatus(int status, int userId);

    /**
     * get all bookings for admin
     */
    Page<BookingListDTO> getAllBookingForAdmin(Pageable pageable);

    /**
     * get booking by booking id
     */
    UserBookingDTO getBookingById(int bookingId);

    /**
     * get booking by hotel id
     */
    Page<UserBookingDTO> getBookingsByHotelId(int hotelId, int status, Pageable pageable);

    /**
     * cancel a booking
     */
    void cancelBooking(int bookingId);

    /**
     * add a new booking
     */
    Integer addNewBooking(BookingRequest bookingRequest);

    /**
     * cancel a booking
     */
    void completeBooking(int bookingId);

    /**
     * cancel a booking
     */
    void updateBookingType(int bookingId, int type);

}
