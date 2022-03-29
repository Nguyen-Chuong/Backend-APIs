package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter @Setter
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

    private List<BookingDetailRequest> bookingDetail;

    private String otherRequirement;

    private int type;

}
