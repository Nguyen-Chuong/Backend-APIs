package com.capstone_project.hbts.dto.Report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ResponseDTO {

    private String message;

    private Timestamp modifyDate;

}
