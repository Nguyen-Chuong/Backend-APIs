package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserRequest {

    private int userId;

    private String message;

    private Timestamp modifyDate; // not required

    private int adminId;

    private int feedbackId;

}
