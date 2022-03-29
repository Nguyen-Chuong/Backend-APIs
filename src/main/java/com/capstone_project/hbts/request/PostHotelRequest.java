package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class PostHotelRequest {

    private int hotelId;

    private Timestamp requestDate; // not required

    private int providerId; // not required

    private int status; // not required

}
