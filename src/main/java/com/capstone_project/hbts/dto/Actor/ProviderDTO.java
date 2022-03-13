package com.capstone_project.hbts.dto.Actor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
