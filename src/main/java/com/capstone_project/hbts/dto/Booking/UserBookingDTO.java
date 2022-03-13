package com.capstone_project.hbts.dto.Booking;

import com.capstone_project.hbts.dto.Hotel.HotelDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class UserBookingDTO {

    private Integer id;

    private Timestamp checkIn;

    private Timestamp checkOut;

    private int status;

    private int reviewStatus;

    private HotelDTO hotel;

    private BigDecimal totalPaid;

    private Timestamp bookingDate;

    private int bookedQuantity;

}
