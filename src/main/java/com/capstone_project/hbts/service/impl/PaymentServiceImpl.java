package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.constant.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.request.PaymentRequest;
import com.capstone_project.hbts.dto.payment.PaymentResultDTO;
import com.capstone_project.hbts.service.PaymentService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final AtomicLong idCounter = new AtomicLong();

    private final DataDecryption dataDecryption;

    public PaymentServiceImpl(DataDecryption dataDecryption) {
        this.dataDecryption = dataDecryption;
    }

    public static String createID(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date()) + idCounter.getAndIncrement();
    }

    @SneakyThrows
    @Override
    public PaymentResultDTO createPayment(PaymentRequest paymentRequest) {
        // create new hashmap to store vnpay params
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", ValidateConstant.VNP_VERSION);
        vnp_Params.put("vnp_Command", ValidateConstant.VNP_COMMAND);
        vnp_Params.put("vnp_TmnCode", ValidateConstant.VNP_TMN_CODE);
        vnp_Params.put("vnp_Amount", String.valueOf(paymentRequest.getAmount()));
        vnp_Params.put("vnp_BankCode", null);
        // get current time zone to put created date
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(calendar.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_CurrCode", ValidateConstant.VNP_CURRENCY_CODE);
        vnp_Params.put("vnp_Locale", ValidateConstant.VNP_LOCALE);
        vnp_Params.put("vnp_OrderInfo", paymentRequest.getDescription());
        vnp_Params.put("vnp_IpAddr", paymentRequest.getIpAddress());
        vnp_Params.put("vnp_OrderType", ValidateConstant.VNP_ORDER_TYPE);
        vnp_Params.put("vnp_ReturnUrl", ValidateConstant.VNP_RETURN_URL);
        vnp_Params.put("vnp_TxnRef", createID());
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
                // build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // build query
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
        // secure hash
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        // generate payment url
        String paymentUrl = ValidateConstant.VNP_API_URL + "?" + queryUrl;
        PaymentResultDTO paymentResultDTO = new PaymentResultDTO();
        paymentResultDTO.setStatus("00");
        paymentResultDTO.setMessage("success");
        paymentResultDTO.setUrl(paymentUrl);
        return paymentResultDTO;
    }

}
