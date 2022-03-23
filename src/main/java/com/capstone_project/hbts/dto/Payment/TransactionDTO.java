package com.capstone_project.hbts.dto.Payment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

    private Integer id;

    private long amount; // divide 100

    private String bankCode;

    private String bankTranNo;

    private String cardType;

    private String orderInfo;

    private String payDate; // parse to date time

    private String responseCode; // 00 success - others

    private String transactionNo;

    private long idService; // unique

}
