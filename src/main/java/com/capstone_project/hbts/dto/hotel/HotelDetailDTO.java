package com.capstone_project.hbts.dto.hotel;

import com.capstone_project.hbts.dto.actor.ProviderDTO;
import com.capstone_project.hbts.dto.location.DistrictDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class HotelDetailDTO {

    private Integer id;

    private String name;

    private String address;

    private String avatar;

    private String description;

    private int status; // 1-active, 2-deactivated

    private DistrictDTO district;

    private ProviderDTO provider;

}
