package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Actor.UserDTO;
import com.capstone_project.hbts.request.ManagerRequest;
import com.capstone_project.hbts.request.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    /**
     * Register an user
     * @param userRequest
     */
    void register(UserRequest userRequest);

    /**
     * Load detail user by email
     * @param email
     */
    UserDTO loadUserByEmail(String email);

    /**
     * Get detail user by username
     * @param username
     */
    UserDTO getUserProfile(String username);

    /**
     * Change password
     * @param username
     * @param newPass
     */
    void changeUserPassword(String username, String newPass);

    /**
     * Get old password
     * @param username
     */
    String getOldPassword(String username);

    /**
     * Update user profile
     * @param userDTO
     */
    void updateUserProfile(UserDTO userDTO);

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
     * Update vip status
     * @param userId
     */
    void updateVipStatus(int userId);

    /**
     * Change forgot password
     * @param email
     * @param newPass
     */
    void changeForgotPassword(String email, String newPass);

    /**
     * Add new manager
     * @param managerRequest
     */
    void addNewManager(ManagerRequest managerRequest);

    /**
     * Get page of all user
     * @param pageable
     */
    Page<UserDTO> getAllUser(Pageable pageable);

}
