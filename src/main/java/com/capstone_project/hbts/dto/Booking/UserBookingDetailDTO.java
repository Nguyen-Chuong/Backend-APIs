package com.capstone_project.hbts.dto.Booking;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserBookingDetailDTO {

    private Integer id;

    private BigDecimal paid;  // this is the price that user have to pay for this room type, = roomType get price ()

    private int roomTypeId;

    private int quantity;

}
