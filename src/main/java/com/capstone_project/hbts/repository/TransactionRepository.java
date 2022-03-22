package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "select t.id from Transaction t where t.transactionNo = :transactionNo")
    Integer getIdByTransactionNo(@Param("transactionNo") String transactionNo);

}
