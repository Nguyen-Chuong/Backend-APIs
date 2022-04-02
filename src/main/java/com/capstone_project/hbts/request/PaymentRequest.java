package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PaymentRequest {

    private Integer idService; // unique - not required

    private long amount;

    private String description;

    private String ipAddress;

}
