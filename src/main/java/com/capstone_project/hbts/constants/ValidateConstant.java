package com.capstone_project.hbts.constants;

public class ValidateConstant {

    public static final String PAGE = "0";

    public static final String PER_PAGE = "10";
    // constants config otp
    public static final String OTP_MESSAGE = "Verification code:";
    // constants email
    public static final String EMAIL_SUBJECT = "[Verify] Please verify your email";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    // constants payment
    public static final String VNP_VERSION= "2.1.0";

    public static final String VNP_COMMAND= "pay";

    public static final String VNP_TMN_CODE= "T8H752OS";

    public static final String VNP_CURRENCY_CODE = "VND";

    public static final String VNP_IP_ADDRESS = "192.168.1.4"; // get from client

    public static final String VNP_LOCALE = "vn";

    public static final String VNP_ORDER_TYPE = "170000";

    public static final String VNP_RETURN_URL = "https://travesily.herokuapp.com/transaction-info"; // component

    public static final String VNP_API_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public static final String VNP_SECURE_HASH = "RBWJEYLNKTBIOMEYXNTCIOIFKIEGCBJX";

}
