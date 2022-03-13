package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ResponseAdminRequest {

    private int adminId;

    private String message;

    private Timestamp modifyDate; // not required

    private String username;

    private int feedbackId;

}
