package com.capstone_project.hbts.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtRequest {

    private Integer id;

    private String jwt;

}
