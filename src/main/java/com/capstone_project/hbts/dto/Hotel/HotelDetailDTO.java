package com.capstone_project.hbts.dto.Hotel;

import com.capstone_project.hbts.dto.Actor.ProviderDTO;
import com.capstone_project.hbts.dto.Location.DistrictDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
