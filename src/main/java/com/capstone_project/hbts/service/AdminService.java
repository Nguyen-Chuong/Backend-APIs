package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Actor.UserDTO;
import com.capstone_project.hbts.request.ManagerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    /**
     * Add new manager
     */
    void addNewManager(ManagerRequest managerRequest);

    /**
     * Get page of all user
     */
    Page<UserDTO> getAllUser(Pageable pageable);

    /**
     * Get list all manager
     */
    List<UserDTO> getListManager();

    /**
     * Deactivate/ delete a manager
     */
    void deleteManager(int userId);

}
