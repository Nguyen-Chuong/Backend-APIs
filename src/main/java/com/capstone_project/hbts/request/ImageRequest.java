package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ImageRequest {

    private Integer roomTypeId;

    private List<String> sourceUrl;

}
