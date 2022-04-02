package com.capstone_project.hbts.dto.room;

import com.capstone_project.hbts.dto.benefit.BenefitDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RoomBenefitDTO {

    private Integer id;

    private BenefitDTO benefit;

}
