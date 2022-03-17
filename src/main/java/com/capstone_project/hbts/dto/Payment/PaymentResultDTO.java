package com.capstone_project.hbts.dto.Payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentResultDTO {

    private String status;

    private String message;

    private String url;

}