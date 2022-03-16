package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Payment.PaymentDTO;
import com.capstone_project.hbts.dto.Payment.PaymentResultDTO;

public interface PaymentService {

    /**
     * create payment
     * @param paymentDTO
     */
    PaymentResultDTO createPayment(PaymentDTO paymentDTO);

}
