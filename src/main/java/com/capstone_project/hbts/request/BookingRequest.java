package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class BookingRequest {

    private int bookedQuantity;

    private Timestamp bookingDate; // not required

    private Timestamp checkIn;

    private Timestamp checkOut;

    private int reviewStatus; // not required

    private int status; // not required

    private int hotelId;

    private int userId; // not required

}
