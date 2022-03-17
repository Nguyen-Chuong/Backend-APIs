package com.capstone_project.hbts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private long amount;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_tran_no")
    private String bankTranNo; // transaction no at bank

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "order_info")
    private String orderInfo;

    @Column(name = "pay_date")
    private String payDate; // pay date at format yyyyMMddHHmmss

    @Column(name = "response_code")
    private String responseCode; // 00 success - others

    @Column(name = "transaction_no")
    private String transactionNo; // transaction no at vnpay

    @Column(name = "id_service")
    private long idService; // unique

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
