package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.request.PaymentRequest;
import com.capstone_project.hbts.dto.Payment.PaymentResultDTO;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * @apiNote the first time booking, user have to choose payment method immediately
     * Since the second times, user can choose cod, so they can pay the money at hotel
     */
    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        log.info("REST request to create payment");
        try {
            PaymentResultDTO paymentResultDTO = paymentService.createPayment(paymentRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, paymentResultDTO, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
