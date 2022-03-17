package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Payment.TransactionDTO;

public interface TransactionService {

    // view list transactions of an user for admin

    /**
     * get a transaction info
     * @param amount
     * @param bankCode
     * @param bankTranNo
     * @param cardType
     * @param orderInfo
     * @param payDate
     * @param responseCode
     * @param transactionNo
     * @param idService
     * @param userId
     */
    TransactionDTO getTransactionInfo(long amount, String bankCode, String bankTranNo, String cardType,
                                      String orderInfo, String payDate, String responseCode, String transactionNo,
                                      int idService, int userId);

}
