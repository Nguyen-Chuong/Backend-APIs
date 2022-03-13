package com.capstone_project.hbts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VipDTO {

    private Integer id;

    private String nameVip;

    private int rangeStart;

    private int rangeEnd;

    private int discount;

}
