package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.service.OTPService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {

    private static final Integer EXPIRE_MINUTES = 3;

    private final LoadingCache<String, Integer> otpCache;

    public OTPServiceImpl(){
        otpCache = CacheBuilder.newBuilder().expireAfterAccess(EXPIRE_MINUTES, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) {
                        return 0;
                    }
                });
    }

    // This method is used to push the otp number with Key. Rewrite the OTP if it exists
    // Using email as key
    @Override
    public int generateOtp(String key) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }

    // This method is used to return the otp number with Key -> Key values is email
    @Override
    public int getOtp(String key) {
        try {
            return otpCache.get(key);
        } catch (Exception e) {
            return 0;
        }
    }

    //This method is used to clear the OTP cache already
    @Override
    public void clearOtp(String key) {
        otpCache.invalidate(key);
    }

}
