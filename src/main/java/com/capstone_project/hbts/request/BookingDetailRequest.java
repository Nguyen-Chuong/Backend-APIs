package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BookingDetailRequest {

    private BigDecimal paid; // price for this room type

    private int quantity; // number of room type

    private int roomTypeId;

}
