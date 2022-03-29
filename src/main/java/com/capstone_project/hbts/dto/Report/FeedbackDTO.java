package com.capstone_project.hbts.dto.Report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class FeedbackDTO {

    private Integer id;

    private int type;

    private String senderName;

    private int receiverId;

    private String message;

    private Timestamp modifyDate;

    private int isProcessed;

    private String email;

}
