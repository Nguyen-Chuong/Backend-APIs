package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Payment.PaymentDTO;
import com.capstone_project.hbts.dto.Payment.PaymentResultDTO;

public interface PaymentService {

    /**
     * create payment
     */
    PaymentResultDTO createPayment(PaymentDTO paymentDTO);

}
