package com.capstone_project.hbts.dto.benefit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class BenefitTypeDTO {

    private Integer id;

    private String name;

    private String icon;

}
