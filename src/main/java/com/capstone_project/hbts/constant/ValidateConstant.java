package com.capstone_project.hbts.constant;

public class ValidateConstant {

    public static final String PAGE = "0";

    public static final String PER_PAGE = "10";

    // mail otp
    public static final String EMAIL_SUBJECT_OTP = "[Verify] Please verify your email";

    public static String getOtpVerifyContent(int otp){
        return "Hi, \n\nIf you did not make this request, please ignore this email.\nEnter the OTP code below to verify your email " +
                "\n\nVerification code: " + otp + "\n\nThanks \nRegards, \n\nThis email was sent by Travesily Service Center";
    }

    // mail cancel
    public static final String EMAIL_SUBJECT_CANCEL = "[Travesily Booking Cancelled] Booking ID ";

    public static String getCancelBookingContent(){
        return "<p>Hi, </p></br><p>We have confirmed the cancellation of your reservation</p></br>"
                + "<p>Thank you for booking with Travesily!</p></br>"
                + "<p>Thanks, regards </p> <p>This email was sent by Travesily Service Center</p></br>";
    }

    // mail confirm
    public static final String EMAIL_SUBJECT_CONFIRM = "[Confirmation] Reservation number Booking ID ";

    public static String getConfirmBookingContent(){
        return "<p>Hi, </p></br><p>Your booking is now confirmed !</p></br>" + "<p>Thank you for booking with Travesily!</p></br>"
                + "<p>Thanks, regards </p> <p>This email was sent by Travesily Service Center</p></br>";
    }

    // mail response user feedback
    public static final String EMAIL_SUBJECT_RESPONSE = "[Response] Travesily Service Center";

    public static String getResponseFeedbackContent(String message){
        return "Hi, \n\nHere is new our response for your feedback: \n" + "'" + message + "'" +
                "\n\nThank you for contacting Travesily. We hope you have received the help you need."
                + "\n\nThanks \nRegards, \n\nThis email was sent by Travesily Service Center";
    }

    // constants payment
    public static final String VNP_VERSION= "2.1.0";

    public static final String VNP_COMMAND= "pay";

    public static final String VNP_TMN_CODE= "T8H752OS";

    public static final String VNP_CURRENCY_CODE = "VND";

    public static final String VNP_LOCALE = "vn";

    public static final String VNP_ORDER_TYPE = "170000";

    public static final String VNP_RETURN_URL = "https://travesily.software/book/transaction-info";

    public static final String VNP_API_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public static final String VNP_SECURE_HASH = "RBWJEYLNKTBIOMEYXNTCIOIFKIEGCBJX";

}
