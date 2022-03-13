package com.capstone_project.hbts.dto.Facility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectFacility {

    private Integer id;

    private String name;

    private String icon;

    private List<FacilityResult> facilities;

}
