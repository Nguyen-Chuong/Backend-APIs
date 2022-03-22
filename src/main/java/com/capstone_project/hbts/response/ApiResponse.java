package com.capstone_project.hbts.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;

    private T data;

    private String error_code;

    private String error_message;

}
