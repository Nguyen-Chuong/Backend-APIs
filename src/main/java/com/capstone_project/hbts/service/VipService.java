package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.VipDTO;

import java.util.List;

public interface VipService {

    /**
     * get all vip info
     * @param
     */
    List<VipDTO> getVipStatus();

    /**
     * update vip info
     * @param discount
     * @param rangeEnd
     * @param rangeStart
     * @param id
     */
    void updateVipClass(int discount, int rangeStart, int rangeEnd, Integer id);

}
