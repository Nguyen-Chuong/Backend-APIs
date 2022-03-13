package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class FeedbackRequest {

    private int type;

    private int senderId;

    private String message;

    private Timestamp modifyDate; // not required

    private int isProcessed; // not required

}
