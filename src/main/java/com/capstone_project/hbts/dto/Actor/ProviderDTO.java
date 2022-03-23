package com.capstone_project.hbts.dto.Actor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProviderDTO {

    private Integer id;

    private String username;

    private String providerName;

    private int status;

    private String email;

    private String phone;

    private String address;

}
