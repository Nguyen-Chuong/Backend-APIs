package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class BookingDetailRequest {

    private BigDecimal paid; // price for this room type

    private int quantity; // number of room type

    private int roomTypeId;

}
