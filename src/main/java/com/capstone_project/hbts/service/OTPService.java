package com.capstone_project.hbts.service;

public interface OTPService {

    /**
     * generate otp
     */
    int generateOtp(String key);

    /**
     * get otp
     */
    int getOtp(String key);

    /**
     * clear otp
     */
    void clearOtp(String key);

}
