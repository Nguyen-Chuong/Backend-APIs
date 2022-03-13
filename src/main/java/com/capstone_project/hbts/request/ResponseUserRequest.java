package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ResponseUserRequest {

    private int userId;

    private String message;

    private Timestamp modifyDate; // not required

    private int adminId;

    private int feedbackId;

}
