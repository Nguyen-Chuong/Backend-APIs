package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.location.DistrictDTO;
import com.capstone_project.hbts.dto.location.DistrictSearchDTO;
import com.capstone_project.hbts.dto.location.ResultSearch;

import java.util.List;

public interface DistrictService {

    /**
     * search districts city
     */
    List<ResultSearch> searchDistrictCity(String text);

    /**
     * get all districts in city
     */
    List<DistrictSearchDTO> getAllDistrictInCity(int cityId);

    /**
     * get top hot location
     */
    List<DistrictDTO> getTopHotLocation(int limit);

}
