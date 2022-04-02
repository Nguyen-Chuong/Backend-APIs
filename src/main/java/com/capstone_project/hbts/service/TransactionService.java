package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.payment.TransactionDTO;

public interface TransactionService {

    /**
     * get a transaction info
     */
    TransactionDTO getTransactionInfo(long amount, String bankCode, String bankTranNo, String cardType, String orderInfo,
                                      String payDate, String responseCode, String transactionNo, long idService, int userId);

    /**
     * to check if transaction no existed
     */
    boolean isTransactionExisted(String transactionNo);

}
