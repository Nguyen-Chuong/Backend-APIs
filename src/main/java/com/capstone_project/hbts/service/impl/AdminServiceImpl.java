package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.actor.UserDTO;
import com.capstone_project.hbts.entity.Role;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.RoleRepository;
import com.capstone_project.hbts.repository.UserRepository;
import com.capstone_project.hbts.request.ManagerRequest;
import com.capstone_project.hbts.response.CustomPageImpl;
import com.capstone_project.hbts.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addNewManager(ManagerRequest managerRequest) {
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
        List<Users> usersList = userRepository.findAllUser(pageable).getContent();
        List<UserDTO> userDTOList = usersList.stream().map(item -> modelMapper.map(item, UserDTO.class)).collect(Collectors.toList());
        return new CustomPageImpl<>(userDTOList);
    }

    @Override
    public List<UserDTO> getListManager() {
        return userRepository.findAllManager().stream().map(item -> modelMapper.map(item, UserDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteManager(int userId) {
        userRepository.deleteManager(userId);
        roleRepository.deleteManagerRole(userId);
    }

}
