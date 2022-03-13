package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Actor.UserDTO;
import com.capstone_project.hbts.entity.Role;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.RoleRepository;
import com.capstone_project.hbts.repository.UserRepository;
import com.capstone_project.hbts.request.ManagerRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addNewManager(ManagerRequest managerRequest) {
        log.info("Request to add new manager");

        // type 0 is normal user, 1 is manager and 2 admin, register is always user
        managerRequest.setType(1);
        // set active for new manager: 1-active, 0-deleted
        managerRequest.setStatus(1);
        // name prefix for user table
        managerRequest.setUsername("u-" + managerRequest.getUsername());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Users newManager = modelMapper.map(managerRequest, Users.class);
        newManager.setPassword(bCryptPasswordEncoder.encode(managerRequest.getPassword()));
        userRepository.save(newManager);
        Role role = new Role(newManager, "ROLE_MANAGER");
        roleRepository.save(role);
    }

    @Override
    public Page<UserDTO> getAllUser(Pageable pageable) {
        log.info("Request to get all user for admin");
        List<Users> usersList = userRepository.findAllUser(pageable).getContent();

        List<UserDTO> userDTOList = usersList.stream()
                .map(item -> modelMapper.map(item, UserDTO.class))
                .collect(Collectors.toList());

        return new CustomPageImpl<>(userDTOList);
    }

    @Override
    public List<UserDTO> getListManager() {
        log.info("Request to get all manager for admin");
        return userRepository.findAllManager()
                .stream()
                .map(item -> modelMapper.map(item, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteManager(int userId) {
        log.info("Request to delete a manager for admin");
        userRepository.deleteManager(userId);
        roleRepository.deleteManagerRole(userId);
    }

}
