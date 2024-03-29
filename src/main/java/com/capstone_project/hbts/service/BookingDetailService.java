package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.booking.UserBookingDetailDTO;

import java.util.List;

public interface BookingDetailService {

    /**
     * get all user booking details by booking id
     */
    List<UserBookingDetailDTO> getAllBookingDetail(int bookingId);

}
