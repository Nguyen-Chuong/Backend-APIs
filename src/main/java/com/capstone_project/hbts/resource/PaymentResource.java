package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.constants.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Payment.PaymentDTO;
import com.capstone_project.hbts.dto.Payment.PaymentResultDTO;
import com.capstone_project.hbts.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RestController
@CrossOrigin
@Log4j2
@RequestMapping("api/v1")
public class PaymentResource {

    private final DataDecryption dataDecryption;

    public PaymentResource(DataDecryption dataDecryption) {
        this.dataDecryption = dataDecryption;
    }

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        log.info("REST request to create payment");

        try {
            // create new hashmap to store vnpay params
            Map<String, String> vnp_Params = new HashMap<>();

            vnp_Params.put("vnp_Version", ValidateConstant.VNP_VERSION);
            vnp_Params.put("vnp_Command", ValidateConstant.VNP_COMMAND);
            vnp_Params.put("vnp_TmnCode", ValidateConstant.VNP_TMN_CODE);
            vnp_Params.put("vnp_Amount", String.valueOf(paymentDTO.getAmount()));
            vnp_Params.put("vnp_BankCode", paymentDTO.getBankCode());
            // get current time zone to put created date
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(calendar.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            vnp_Params.put("vnp_CurrencyCode", ValidateConstant.VNP_CURRENCY_CODE);
            vnp_Params.put("vnp_Locale", ValidateConstant.VNP_LOCALE);
            vnp_Params.put("vnp_OrderInfo", paymentDTO.getDescription());
            vnp_Params.put("vnp_OrderType", ValidateConstant.VNP_ORDER_TYPE);
            vnp_Params.put("vnp_ReturnUrl", ValidateConstant.VNP_RETURN_URL);
            vnp_Params.put("vnp_TxnRef", String.valueOf(1));
            vnp_Params.put("vnp_SecureHash", ValidateConstant.VNP_SECURE_HASH);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            // create new hash data string
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator<String> iterator = fieldNames.iterator();

            while (iterator.hasNext()) {
                String fieldName = iterator.next();
                String fieldValue = vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    // Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    // Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (iterator.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            // after encrypt data
            String vnp_SecureHash = dataDecryption.hmacSHA512(ValidateConstant.VNP_SECURE_HASH, hashData.toString());
            // generate payment url
            String paymentUrl = ValidateConstant.VNP_API_URL + "?" + queryUrl;

            // return result w/ payment url
            PaymentResultDTO resultDTO = new PaymentResultDTO();
            resultDTO.setStatus("00");
            resultDTO.setMessage("success");
            resultDTO.setUrl(paymentUrl);

            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, resultDTO,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
