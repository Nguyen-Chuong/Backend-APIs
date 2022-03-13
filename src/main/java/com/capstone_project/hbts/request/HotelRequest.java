package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelRequest {

    private String name;

    private String address;

    private String avatar;

    private String description;

    private String phone;

    private String email;

    private int status; // not required

    private int districtId; // drop down list to pick

    private int providerId; // not required

}
