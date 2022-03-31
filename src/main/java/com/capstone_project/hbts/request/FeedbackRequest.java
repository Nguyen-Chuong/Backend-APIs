package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class FeedbackRequest {

    private int type;

    private int senderId;

    private String message;

    private Timestamp modifyDate; // not required

    private int isProcessed; // not required

    private String email;

    private int bookingId;

    private String phone;

}
