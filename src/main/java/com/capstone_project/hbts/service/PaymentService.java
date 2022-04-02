package com.capstone_project.hbts.service;

import com.capstone_project.hbts.request.PaymentRequest;
import com.capstone_project.hbts.dto.payment.PaymentResultDTO;

public interface PaymentService {

    /**
     * create payment
     */
    PaymentResultDTO createPayment(PaymentRequest paymentRequest);

}
