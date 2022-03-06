package com.capstone_project.hbts.dto.Hotel;

import com.capstone_project.hbts.dto.Location.DistrictDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class HotelDTO {

    private Integer id;

    private String name;

    private String address;

    private String avatar;

    private String description;

    private BigDecimal lowestPrice;

    private int status; // 1-active, 2-deactivated, 3-pending, 4-banned

    private String phone;

    private String email;

    private DistrictDTO district;

}
