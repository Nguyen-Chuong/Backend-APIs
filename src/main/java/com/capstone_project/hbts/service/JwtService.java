package com.capstone_project.hbts.service;

import com.capstone_project.hbts.request.JwtRequest;

public interface JwtService {

    /**
     * Save token key for admin
     */
    void saveTokenKeyForAdmin(JwtRequest jwtRequest);

    /**
     * Get token key for admin
     */
    String getTokenKeyForAdmin();

}
