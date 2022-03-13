package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Actor.ProviderDTO;
import com.capstone_project.hbts.request.ProviderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProviderService {

    /**
     * Load provider by email
     * @param email
     */
    ProviderDTO loadProviderByEmail(String email);

    /**
     * Register an provider
     * @param providerRequest
     */
    void register(ProviderRequest providerRequest);

    /**
     * Check duplicate username
     * @param username
     */
    boolean isUsernameExist(String username);

    /**
     * Check duplicate email
     * @param email
     */
    boolean isEmailExist(String email);

    /**
     * Get detail provider by username
     * @param username
     */
    ProviderDTO getProviderProfile(String username);

    /**
     * Update an provider
     * @param providerDTO
     */
    void updateProviderProfile(ProviderDTO providerDTO);

    /**
     * Change provider 's password
     * @param username
     * @param newPass
     */
    void changeProviderPassword(String username, String newPass);

    /**
     * Get old password
     * @param username
     */
    String getOldPassword(String username);

    /**
     * Get page of all provider
     * @param pageable
     */
    Page<ProviderDTO> getAllProvider(Pageable pageable);

    /**
     * ban provider for admin
     * @param providerId
     * @return
     */
    void banProvider(int providerId);

}
