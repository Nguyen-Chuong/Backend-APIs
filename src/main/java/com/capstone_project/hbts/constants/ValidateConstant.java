package com.capstone_project.hbts.constants;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class ValidateConstant {

    public static final String PAGE = "0";

    public static final String PER_PAGE = "10";
    // constants config otp
    public static final String OTP_MESSAGE = "Verification code:";
    // constants email
    public static final String EMAIL_SUBJECT = "[Verify] Please verify your email";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    // contants payment
    public static final String VPN_VERSION= "2.1.0";

    public static final String VPN_COMMAND= "pay";

    public static final String VPN_TMNCODE= "89UN2O98";

    public static final String VPN_CURRCODE = "VND";

    public static final String VPN_IPADDR = "0:0:0:0:0:0:0:0:1";

    public static final String VPN_LOCALE = "vn";

    public static final String VPN_ORDERTYPE = "170000";

    public static final String VPN_RETURNURL = "http://localhost:4200/thong-tin-thanh-toan";

    public static final String VPN_APIURL = "https://sandbox.vnpayment.vn/merchant_webapi/merchant.html";

    public static final String VPN_SECUREHASH = "SJVFNMHGICNUKZTCNJZJCSTHXPGQBICL";

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
}
