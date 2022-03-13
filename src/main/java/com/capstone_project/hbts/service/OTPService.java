package com.capstone_project.hbts.service;

public interface OTPService {

    /**
     * generate otp
     * @param key
     */
    int generateOtp(String key);

    /**
     * get otp
     * @param key
     */
    int getOtp(String key);

    /**
     * clear otp
     * @param key
     */
    void clearOtp(String key);

}
