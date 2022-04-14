package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Coupon;
import com.capstone_project.hbts.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CouponServiceImpl implements CouponService {

    private final Coupon coupon;

    public CouponServiceImpl(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    public Coupon getCoupon() {
        return this.coupon;
    }

    @Override
    public void setCoupon(String code, long discount, Date dateExpired) {
        this.coupon.setCode(code);
        this.coupon.setDiscount(discount);
        this.coupon.setDateExpired(dateExpired);
    }

}
