package com.capstone_project.hbts.dto.Benefit;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BenefitDTO {

    private Integer id;

    private String name;

    private String icon;

    private BenefitTypeDTO benefitType;

}
