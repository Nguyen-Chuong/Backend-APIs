package com.capstone_project.hbts.dto.report;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@NoArgsConstructor
public class ResponseDTO {

    private String message;

    private Timestamp modifyDate;

    private int sendBy;

}
