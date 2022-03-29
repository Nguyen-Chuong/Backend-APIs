package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Payment.TransactionDTO;
import com.capstone_project.hbts.entity.Transaction;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl{

    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    public TransactionDTO getTransactionInfo(long amount, String bankCode, String bankTranNo, String cardType,
                                             String orderInfo, String payDate, String responseCode, String transactionNo,
                                             long idService, int userId) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setBankCode(bankCode);
        transaction.setBankTranNo(bankTranNo);
        transaction.setCardType(cardType);
        transaction.setOrderInfo(orderInfo);
        transaction.setPayDate(payDate);
        transaction.setResponseCode(responseCode);
        transaction.setTransactionNo(transactionNo);
        transaction.setIdService(idService);
        // set user for transaction
        Users users = new Users();
        users.setId(userId);
        transaction.setUsers(users);
        // save it to db and get record
        Transaction transactionRecord = transactionRepository.save(transaction);
        // convert to DTO and return
        return modelMapper.map(transactionRecord, TransactionDTO.class);
    }

    public boolean isTransactionExisted(String transactionNo) {
        return transactionRepository.getIdByTransactionNo(transactionNo) != null;
    }

}
