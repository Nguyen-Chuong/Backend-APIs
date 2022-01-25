package com.capstone_project.hbts.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String jwttoken;

    private String username;

    private String avatar;

}
