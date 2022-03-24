package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Booking.BookingListDTO;
import com.capstone_project.hbts.dto.Booking.UserBookingDTO;
import com.capstone_project.hbts.request.BookingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    /**
     * get all bookings
     * @param userId
     */
    List<UserBookingDTO> getAllBookings(int userId);

    /**
     * get all bookings need to review or done
     * @param reviewStatus
     * @param userId
     */
    List<UserBookingDTO> getAllBookingsReview(int reviewStatus, int userId);

    /**
     * count number of all bookings completed by user id
     * @param userId
     */
    int getNumberBookingsCompleted(int userId);

    /**
     * get all bookings by status
     * @param status
     * @param userId
     */
    List<UserBookingDTO> getAllBookingsByStatus(int status, int userId);

    /**
     * get all bookings for admin
     * @param pageable
     */
    Page<BookingListDTO> getAllBookingForAdmin(Pageable pageable);

    /**
     * get booking by booking id
     * @param bookingId
     */
    UserBookingDTO getBookingById(int bookingId);

    /**
     * get booking by hotel id
     * @param hotelId
     * @param pageable
     */
    Page<UserBookingDTO> getBookingsByHotelId(int hotelId, Pageable pageable);

    /**
     * cancel a booking
     * @param bookingId
     */
    void cancelBooking(int bookingId);

    /**
     * add a new booking
     * @param bookingRequest
     * @return bookingId just inserted
     */
    Integer addNewBooking(BookingRequest bookingRequest);

    /**
     * @apiNote complete a booking: if pay: call this api after that
     * cancel -> status 3 -> request refund (only before > 1 day)
     * if cod: hotel confirm -> completed, cancel -> free
     * @param bookingId
     */
    void completeBooking(int bookingId);

    /**
     * to update type of booking: 1 - cod, 2 - ATM
     * @param bookingId
     * @param type
     */
    void updateBookingType(int bookingId, int type);

}
