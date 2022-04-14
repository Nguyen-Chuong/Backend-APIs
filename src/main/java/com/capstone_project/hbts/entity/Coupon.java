package com.capstone_project.hbts.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Setter @Getter
public class Coupon {

    private String code;

    private long discount;

    private Date dateExpired;

}
