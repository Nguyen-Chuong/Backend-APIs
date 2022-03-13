package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class PostHotelRequest {

    private int hotelId;

    private Timestamp requestDate; // not required

    private int providerId; // not required

    private int status; // not required

}
