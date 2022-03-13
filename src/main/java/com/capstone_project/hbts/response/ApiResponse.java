package com.capstone_project.hbts.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int status;

    private T data;

    private String error_code;

    private String error_message;

    public ApiResponse(int status, String error_code, String error_message) {
        this.status = status;
        this.error_code = error_code;
        this.error_message = error_message;
    }

}
