package com.capstone_project.hbts.dto.Payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO {

    private Integer idService; // unique - not required

    private long amount;

    private String description;

}
