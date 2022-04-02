package com.capstone_project.hbts.dto.benefit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ObjectBenefit {

    private Integer id;

    private String name;

    private String icon;

    private List<BenefitResult> benefits;

}
