package com.capstone_project.hbts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO {

    private Integer id;

    private int roomTypeId;

    private int hotelId;

    private int quantity;

}
