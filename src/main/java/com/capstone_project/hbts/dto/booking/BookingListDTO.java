package com.capstone_project.hbts.dto.booking;

import com.capstone_project.hbts.dto.hotel.HotelDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class BookingListDTO {

    private Integer id;

    private Timestamp checkIn;

    private Timestamp checkOut;

    private int status;

    private HotelDTO hotel;

    private Timestamp bookingDate;

    private String username;

}
