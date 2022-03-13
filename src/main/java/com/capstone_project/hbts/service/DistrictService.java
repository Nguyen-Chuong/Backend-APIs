package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Location.DistrictSearchDTO;
import com.capstone_project.hbts.dto.Location.ResultSearch;

import java.util.List;

public interface DistrictService {

    /**
     * search districts
     * @param text
     */
    List<DistrictSearchDTO> searchDistrict(String text);

    /**
     * search districts city
     * @param text
     */
    List<ResultSearch> searchDistrictCity(String text);

    /**
     * get all districts in city
     * @param cityId
     */
    List<DistrictSearchDTO> getAllDistrictInCity(int cityId);

}
