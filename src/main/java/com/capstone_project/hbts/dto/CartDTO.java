package com.capstone_project.hbts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {

    private Integer id;

    private int roomTypeId;

    private int hotelId;

    private int quantity;

    private int bookedQuantity;

    private Date dateIn;

    private Date dateOut;

}
