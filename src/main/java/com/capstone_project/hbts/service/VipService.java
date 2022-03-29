package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.VipDTO;

import java.util.List;

public interface VipService {

    /**
     * get all vip info
     */
    List<VipDTO> getVipStatus();

    /**
     * update vip info
     */
    void updateVipClass(int discount, int rangeStart, int rangeEnd, Integer id);

}
