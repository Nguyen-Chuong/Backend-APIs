package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.ChartDTO;
import com.capstone_project.hbts.dto.actor.ProviderDTO;
import com.capstone_project.hbts.request.ProviderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ProviderService {

    /**
     * Load provider by email
     */
    ProviderDTO loadProviderByEmail(String email);

    /**
     * Register an provider
     */
    void register(ProviderRequest providerRequest);

    /**
     * Check duplicate username
     */
    boolean isUsernameExist(String username);

    /**
     * Check duplicate email
     */
    boolean isEmailExist(String email);

    /**
     * Get detail provider by username
     */
    ProviderDTO getProviderProfile(String username);

    /**
     * Update an provider
     */
    void updateProviderProfile(ProviderDTO providerDTO);

    /**
     * Change provider 's password
     */
    void changeProviderPassword(String username, String newPass);

    /**
     * Get old password
     */
    String getOldPassword(String username);

    /**
     * Get page of all provider
     */
    Page<ProviderDTO> getAllProvider(int status, Pageable pageable);

    /**
     * Get number of all provider no paging
     */
    int getNumberProviderNoPaging(int status);

    /**
     * ban provider for admin
     */
    void banProvider(int providerId);

    /**
     * provider change forgot password
     */
    void changeForgotPassword(String email, String newPass);

    /**
     * get chart data for metric
     */
    ChartDTO getChartData(LocalDate fromDate, LocalDate toDate, String providerName);

}
