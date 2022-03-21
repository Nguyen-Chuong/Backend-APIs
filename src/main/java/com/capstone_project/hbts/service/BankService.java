package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.BankDTO;

import java.util.List;

public interface BankService {

    /**
     * Get all banks
     * @param
     */
    List<BankDTO> getAllBanks();

}
