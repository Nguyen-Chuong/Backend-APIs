package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class ResponseAdminRequest {

    private int adminId;

    private String message;

    private Timestamp modifyDate; // not required

    private String username;

    private int feedbackId;

}
