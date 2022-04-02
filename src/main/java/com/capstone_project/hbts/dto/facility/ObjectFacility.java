package com.capstone_project.hbts.dto.facility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ObjectFacility {

    private Integer id;

    private String name;

    private String icon;

    private List<FacilityResult> facilities;

}
