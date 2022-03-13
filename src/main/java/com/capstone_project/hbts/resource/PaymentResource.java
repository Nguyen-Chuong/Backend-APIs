package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ValidateConstant;
import com.capstone_project.hbts.dto.PaymentDTO;
import com.capstone_project.hbts.dto.PaymentResultDTO;
import com.capstone_project.hbts.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@Log4j2
@RequestMapping("api/payment")
public class PaymentResource {
    @PostMapping("create-payment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO requestParams) throws UnsupportedEncodingException {
        Map<String ,  String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version" , ValidateConstant.VPN_VERSION);
        vnp_Params.put("vnp_Command" , ValidateConstant.VPN_COMMAND);
        vnp_Params.put("vnp_TmnCode" , ValidateConstant.VPN_TMNCODE);
        vnp_Params.put("vnp_Amount" , String.valueOf(123223));
        vnp_Params.put("vnp_BankCode" , "ABBANK");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate" , vnp_CreateDate);
        vnp_Params.put("vnp_CurrCode" , ValidateConstant.VPN_CURRCODE);
        vnp_Params.put("vnp_Locale" , ValidateConstant.VPN_LOCALE);
        vnp_Params.put("vnp_OrderInfo" , "ABC");
        vnp_Params.put("vnp_OrderType" , ValidateConstant.VPN_ORDERTYPE);
        vnp_Params.put("vnp_ReturnUrl" , ValidateConstant.VPN_RETURNURL);
        vnp_Params.put("vnp_TxnRef" , String.valueOf(1));
        vnp_Params.put("vnp_SecureHash" , ValidateConstant.VPN_SECUREHASH);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHas = ValidateConstant.hmacSHA512(ValidateConstant.VPN_SECUREHASH , hashData.toString());
        String paymentUrl = ValidateConstant.VPN_APIURL + "?" + queryUrl;
        PaymentResultDTO resultDTO = new PaymentResultDTO();
        resultDTO.setStatus("00");
        resultDTO.setMessage("success");
        resultDTO.setUrl(paymentUrl);
        return ResponseEntity.ok()
                .body(new ApiResponse<>(200, resultDTO,
                        null, null));
    }
}
