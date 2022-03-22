package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "select id from heroku_4fe5c149618a3f9.transaction where transaction_no = :transactionNo",
            nativeQuery = true)
    Integer getIdByTransactionNo(@Param("transactionNo") String transactionNo);

}
