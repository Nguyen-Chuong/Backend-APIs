package com.capstone_project.hbts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VipDTO {

    private Integer id;

    private String nameVip;

    private int rangeStart;

    private int rangeEnd;

    private int discount;

}
