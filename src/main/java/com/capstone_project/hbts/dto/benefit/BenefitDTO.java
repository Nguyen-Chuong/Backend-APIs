package com.capstone_project.hbts.dto.benefit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BenefitDTO {

    private Integer id;

    private String name;

    private String icon;

    private BenefitTypeDTO benefitType;

}
