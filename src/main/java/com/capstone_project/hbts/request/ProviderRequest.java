package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
