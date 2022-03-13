package com.capstone_project.hbts.dto.Benefit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectBenefit {

    private Integer id;

    private String name;

    private String icon;

    private List<BenefitResult> benefits;

}
