package com.capstone_project.hbts.constants;

public class ValidateConstant {

    public static final String PAGE = "0";

    public static final String PER_PAGE = "10";

    // mail otp
    public static final String EMAIL_SUBJECT_OTP = "[Verify] Please verify your email";

    public static String getOtpVerifyContent(int otp){
        return "Hi, \n\nIf you did not make this request, please ignore this email. " +
                "\nEnter the OTP code below to verify your email \n\nVerification code: " + otp +
                "\n\nThanks \nRegards, \n\nThis email was sent by Travesily Service Center";
    }

    // mail cancel
    public static final String EMAIL_SUBJECT_CANCEL = "Booking Canceled Travesily Booking ID ";

    public static String getCancelBookingContent(){
        return "<h3>Hi Nguyễn Thúc Nguyên Chương</h3></br>" +
                "<h3>We have confirmed the cancellation of your reservation at Hoan Kiem Hotel</h3></br>" +
                "<h3>Thank you for booking with Travesily!</h3></br>";
    }

    // mail confirm
    public static final String EMAIL_SUBJECT_CONFIRM = "Confirmation for reservation number Booking ID ";

    public static String getConfirmBookingContent(){
        return "<tr>\n" +
                "  <td>\n" +
                "    <table\n" +
                "      cellspacing=\"0\"\n" +
                "      cellpadding=\"0\"\n" +
                "      style=\"\n" +
                "        border: 1px solid #dddfe2;\n" +
                "        font-size: 16px;\n" +
                "        border-top: 6px solid #32a923;\n" +
                "        padding: 0;\n" +
                "        margin: 0 auto;\n" +
                "        max-width: 650px;\n" +
                "        border-radius: 8px;\n" +
                "        background-color: #ffffff;\n" +
                "        width: 100%;\n" +
                "      \"\n" +
                "    >\n" +
                "      <tbody>\n" +
                "        <tr style=\"text-align: center\">\n" +
                "          <td\n" +
                "            style=\"\n" +
                "              padding: 0;\n" +
                "              font-size: 20px;\n" +
                "              font-weight: bold;\n" +
                "              color: #32a923;\n" +
                "            \"\n" +
                "          >\n" +
                "            <p style=\"margin: 0; padding: 0\">Your booking is now confirmed !</p>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <tr style=\"text-align: center\">\n" +
                "          <td>\n" +
                "          </br>\n" +
                "            <p style=\"margin: 0; padding: 0\">Hi Nguyễn Thúc Nguyên Chương</p>\n" +
                "          </br>\n" +
                "          </br>\n" +
                "            <p style=\"margin: 0; padding: 0\">\n" +
                "              Thank you for booking with Travesily!\n" +
                "            </p>\n" +
                "          </br>\n" +
                "          </br>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "  </td>\n" +
                "</tr>";
    }

    // mail response user feedback
    public static final String EMAIL_SUBJECT_RESPONSE = "Response from Travesily Customer Service Center";

    public static String getResponseFeedbackContent(String message){
        return "<h3>Hi Nguyễn Thúc Nguyên Chương</h3></br>" +
                "<h3>Here is new our response for your feedback: </h3></br>" +
                message +
                "<h3>Thank you for contacting Travesily. We hope you have received the help you need.</h3></br>" +
                "<h3>Thank you for using our service</h3></br>";
    }

    // constants payment
    public static final String VNP_VERSION= "2.1.0";

    public static final String VNP_COMMAND= "pay";

    public static final String VNP_TMN_CODE= "T8H752OS";

    public static final String VNP_CURRENCY_CODE = "VND";

    public static final String VNP_IP_ADDRESS = "192.168.1.4"; // get from client

    public static final String VNP_LOCALE = "vn";

    public static final String VNP_ORDER_TYPE = "170000";

    public static final String VNP_RETURN_URL = "https://travesily.herokuapp.com/book/transaction-info"; // component

    public static final String VNP_API_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public static final String VNP_SECURE_HASH = "RBWJEYLNKTBIOMEYXNTCIOIFKIEGCBJX";

}
