package com.capstone_project.hbts.dto.booking;

import com.capstone_project.hbts.dto.hotel.HotelDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter @Setter
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

    private String otherRequirement;

    private int type;

    private String email;

    private String username;

}
