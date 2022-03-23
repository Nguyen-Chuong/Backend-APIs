package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ManagerRequest {

    private String username;

    private String password;

    private String firstname;

    private int status; // not required

    private String lastname;

    private String email;

    private String phone;

    private int type; // not required

}
