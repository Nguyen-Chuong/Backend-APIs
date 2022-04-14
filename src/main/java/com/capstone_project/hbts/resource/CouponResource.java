package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.entity.Coupon;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.CouponService;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Log4j2
@RequestMapping("api/v1")
public class CouponResource {

    private final CouponService couponService;

    public CouponResource(CouponService couponService) {
        this.couponService = couponService;
    }

    /**
     * @apiNote to get coupon
     */
    @GetMapping("/get-coupon")
    public ResponseEntity<?> getCouponInfo() {
        log.info("REST request to get coupon");
        try {
            Coupon coupon = couponService.getCoupon();
            return ResponseEntity.ok().body(new ApiResponse<>(200, coupon, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote to set coupon for admin
     */
    @PostMapping("/set-coupon")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCouponInfo(@RequestParam String code, @RequestParam long discount,
                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateExpired) {
        log.info("REST request to update coupon");
        try {
            couponService.setCoupon(code, discount, dateExpired);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
