package com.capstone_project.hbts.dto.Payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {

    private Integer idService; // unique - not required

    private long amount;

    private String description;

}
