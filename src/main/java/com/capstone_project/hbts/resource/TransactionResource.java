package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.dto.payment.TransactionDTO;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.security.jwt.JwtTokenUtil;
import com.capstone_project.hbts.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class TransactionResource {

    private final TransactionService transactionService;

    private final JwtTokenUtil jwtTokenUtil;

    public TransactionResource(TransactionService transactionService, JwtTokenUtil jwtTokenUtil) {
        this.transactionService = transactionService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * @apiNote insert record and get transaction info just process
     */
    @GetMapping("/get-transaction-info")
    public ResponseEntity<?> getTransactionInfo(@RequestHeader("Authorization") String jwttoken, @RequestParam long vnp_Amount,
                                                @RequestParam String vnp_BankCode, @RequestParam String vnp_BankTranNo,
                                                @RequestParam String vnp_CardType, @RequestParam String vnp_OrderInfo,
                                                @RequestParam String vnp_PayDate, @RequestParam String vnp_ResponseCode,
                                                @RequestParam String vnp_TransactionNo, @RequestParam long vnp_TxnRef) {
        log.info("REST request to get detail transaction info that user just process");
        int userId = Integer.parseInt(jwtTokenUtil.getUserIdFromToken(jwttoken.substring(7)));
        try {
            if (transactionService.isTransactionExisted(vnp_TransactionNo)) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_TRAN_001_LABEL));
            } else {
                TransactionDTO transactionDTO = transactionService.getTransactionInfo(vnp_Amount, vnp_BankCode, vnp_BankTranNo,
                        vnp_CardType, vnp_OrderInfo, vnp_PayDate, vnp_ResponseCode, vnp_TransactionNo, vnp_TxnRef, userId);
                return ResponseEntity.ok().body(new ApiResponse<>(200, transactionDTO, null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
