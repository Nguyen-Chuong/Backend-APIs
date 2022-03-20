package com.capstone_project.hbts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
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
