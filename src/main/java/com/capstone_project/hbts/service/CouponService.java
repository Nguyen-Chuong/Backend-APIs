package com.capstone_project.hbts.service;

import com.capstone_project.hbts.entity.Coupon;

import java.util.Date;

public interface CouponService {

    /**
     * get coupon
     */
    Coupon getCoupon();

    /**
     * set coupon
     */
    void setCoupon(String code, long discount, Date dateExpired);

}
