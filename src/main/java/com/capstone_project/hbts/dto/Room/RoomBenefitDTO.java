package com.capstone_project.hbts.dto.Room;

import com.capstone_project.hbts.dto.Benefit.BenefitDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomBenefitDTO {

    private Integer id;

    private BenefitDTO benefit;

}
