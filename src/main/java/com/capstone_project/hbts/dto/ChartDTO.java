package com.capstone_project.hbts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChartDTO {

    private List<String> labels;

    private List<Integer> data;

}
