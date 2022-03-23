package com.capstone_project.hbts.dto.Payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentResultDTO {

    private String status;

    private String message;

    private String url;

}
