package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.constant.ValidateConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.request.FeedbackRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.EmailService;
import com.capstone_project.hbts.service.OTPService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class EmailResource {

    private final EmailService emailService;

    private final OTPService otpService;

    private final DataDecryption dataDecryption;

    public EmailResource(EmailService emailService, OTPService otpService, DataDecryption dataDecryption) {
        this.emailService = emailService;
        this.otpService = otpService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @apiNote server
     */
    @PostMapping("/authenticate/generateOtp")
    public ResponseEntity<?> generateOtp(@RequestParam String email) {
        log.info("REST request to generate otp and send email to user");
        String emailDecrypted;
        try {
            emailDecrypted = dataDecryption.convertEncryptedDataToString(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            int otp = otpService.generateOtp(emailDecrypted);
            emailService.send(emailDecrypted, ValidateConstant.EMAIL_SUBJECT_OTP, ValidateConstant.getOtpVerifyContent(otp));
            return ResponseEntity.ok().body(new ApiResponse<>(200, otp, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote server
     */
    @PostMapping("/authenticate/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otpEncrypted) {
        log.info("REST request to verify otp that user sent");
        String emailDecrypted;
        try {
            emailDecrypted = dataDecryption.convertEncryptedDataToString(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        int otp;
        try {
            otp = dataDecryption.convertEncryptedDataToInt(otpEncrypted);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            // verify otp and otpCache
            int serverOtp = otpService.getOtp(emailDecrypted);
            if (otp <= 0) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_OTP_001_LABEL));
            }
            if (serverOtp <= 0) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_OTP_002_LABEL));
            }
            if (otp == serverOtp) {
                otpService.clearOtp(emailDecrypted);
                return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
            } else {
                return ResponseEntity.ok().body(new ApiResponse<>(400, null, ErrorConstant.ERR_OTP_003_LABEL));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote server to mail user with content cancel booking or confirm booking
     */
    @PostMapping("/mail/process-booking/{status}")
    public ResponseEntity<?> sendMailCancelBooking(@RequestParam String email, @RequestParam String bookingId, @PathVariable int status) {
        log.info("REST request to cancel booking for user");
        String emailDecrypted;
        int id;
        try {
            emailDecrypted = dataDecryption.convertEncryptedDataToString(email);
            id = dataDecryption.convertEncryptedDataToInt(bookingId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            if (status == 1) {
                emailService.sendHTMLMail(emailDecrypted, ValidateConstant.EMAIL_SUBJECT_CANCEL + id,
                        ValidateConstant.getCancelBookingContent());
                return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
            } else if (status == 2) {
                emailService.sendHTMLMail(emailDecrypted, ValidateConstant.EMAIL_SUBJECT_CONFIRM + id,
                        ValidateConstant.getConfirmBookingContent());
                return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
            } else {
                return ResponseEntity.ok().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote for admin/ manager to mail user with content response feedback, call api when admin click to send response
     */
    @PostMapping("/mail/send-response")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<?> sendMailResponseFeedback(@RequestParam String email, @RequestBody FeedbackRequest feedbackRequest) {
        log.info("REST request to send mail response for user's feedback");
        String emailDecrypted;
        try {
            emailDecrypted = dataDecryption.convertEncryptedDataToString(email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            emailService.sendHTMLMail(emailDecrypted, ValidateConstant.EMAIL_SUBJECT_RESPONSE,
                    ValidateConstant.getResponseFeedbackContent(feedbackRequest.getMessage()));
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}