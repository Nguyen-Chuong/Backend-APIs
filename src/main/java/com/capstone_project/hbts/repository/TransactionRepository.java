package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    @Query(value = "select t.id from Transaction t where t.transactionNo = :transactionNo")
    Integer getIdByTransactionNo(@Param("transactionNo") String transactionNo);

}
