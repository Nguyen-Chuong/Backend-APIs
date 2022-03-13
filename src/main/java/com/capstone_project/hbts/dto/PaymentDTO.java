package com.capstone_project.hbts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentDTO {

    private Integer idService; // unique

    private long amount;

    private String description;

    private String bankCode;

}
