package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ProviderRequest {

    private String username;

    private String password;

    private String providerName;

    private int status; // not required

    private String email;

    private String phone;

    private String address;

}
