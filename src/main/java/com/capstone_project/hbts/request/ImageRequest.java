package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ImageRequest {

    private Integer roomTypeId;

    private List<String> sourceUrl;

}