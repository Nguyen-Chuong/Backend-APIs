package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Actor.UserDTO;
import com.capstone_project.hbts.request.UserRequest;

public interface UserService {

    /**
     * Register an user
     */
    void register(UserRequest userRequest);

    /**
     * Load detail user by email
     */
    UserDTO loadUserByEmail(String email);

    /**
     * Get detail user by username
     */
    UserDTO getUserProfile(String username);

    /**
     * Change password
     */
    void changeUserPassword(String username, String newPass);

    /**
     * Get old password
     */
    String getOldPassword(String username);

    /**
     * Update user profile
     */
    void updateUserProfile(UserDTO userDTO);

    /**
     * Check duplicate username
     */
    boolean isUsernameExist(String username);

    /**
     * Check duplicate email
     */
    boolean isEmailExist(String email);

    /**
     * Update vip status
     */
    void updateVipStatus(int userId);

    /**
     * Change forgot password
     */
    void changeForgotPassword(String email, String newPass);

    /**
     * get user id
     */
    int getUserId(String username);

    /**
     * Delete account for user
     */
    void deleteAccount(int userId);

}