package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Actor.UserDTO;
import com.capstone_project.hbts.dto.VipDTO;
import com.capstone_project.hbts.entity.Role;
import com.capstone_project.hbts.entity.Users;
import com.capstone_project.hbts.repository.BookingRepository;
import com.capstone_project.hbts.repository.RoleRepository;
import com.capstone_project.hbts.repository.UserRepository;
import com.capstone_project.hbts.repository.VipRepository;
import com.capstone_project.hbts.request.UserRequest;
import com.capstone_project.hbts.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final BookingRepository bookingRepository;

    private final RoleRepository roleRepository;

    private final VipRepository vipRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BookingRepository bookingRepository,
                           RoleRepository roleRepository, VipRepository vipRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bookingRepository = bookingRepository;
        this.roleRepository = roleRepository;
        this.vipRepository = vipRepository;
    }

    @Override
    public void register(UserRequest userRequest) {
        // type 0 is normal user, 1 is manager and 2 admin, register is always user
        userRequest.setType(0);
        // set active for new user: 1-active, 0-deleted
        userRequest.setStatus(1);
        // name prefix for user table
        userRequest.setUsername("u-" + userRequest.getUsername());
        // set vip status auto 1 for new user
        VipDTO vipDTO = new VipDTO();
        vipDTO.setId(1);
        userRequest.setVip(vipDTO);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Users newUser = modelMapper.map(userRequest, Users.class);
        newUser.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        userRepository.save(newUser);
        Role role = new Role(newUser, "ROLE_USER");
        roleRepository.save(role);
    }

    @Override
    public UserDTO loadUserByEmail(String email) {
        Users users = userRepository.getUsersByEmail(email);
        if(users == null){
            return null;
        }else {
            return modelMapper.map(users, UserDTO.class);
        }
    }

    @Override
    public UserDTO getUserProfile(String username) {
        Users users = userRepository.getUsersByUsername(username);
        if(users == null){
            return null;
        }else {
            return modelMapper.map(users, UserDTO.class);
        }
    }

    @Transactional
    @Override
    public void changeUserPassword(String username, String newPass) {
        userRepository.changePass(username, newPass);
    }

    @Override
    public String getOldPassword(String username) {
        return userRepository.getOldPassword(username);
    }

    @Transactional
    @Override
    public void updateUserProfile(UserDTO userDTO) {
        userRepository.updateUserProfile(userDTO.getFirstname(), userDTO.getLastname(), userDTO.getPhone(),
                userDTO.getAddress(), userDTO.getAvatar(), userDTO.getSpend(), userDTO.getId());
    }

    @Override
    public boolean isUsernameExist(String username) {
        String usernameFromDB = userRepository.getUsername(username);
        return usernameFromDB != null;
    }

    @Override
    public boolean isEmailExist(String email) {
        String emailFromDB = userRepository.getEmail(email);
        return emailFromDB != null;
    }

    @Transactional
    @Override
    public void updateVipStatus(int userId) {
        int numberBookingCompleted = bookingRepository.numberBookingCompleted(userId);
        int vipId = 0;
        // consider number get from db cuz it can be change later
        List<VipDTO> listVipDTO = vipRepository.findAll().stream().map(item -> modelMapper.map(item, VipDTO.class))
                .collect(Collectors.toList());
        for (VipDTO vipDTO : listVipDTO) {
            if (numberBookingCompleted >= vipDTO.getRangeStart()
                    && numberBookingCompleted <= vipDTO.getRangeEnd()) {
                vipId = vipDTO.getId();
            }
        }
        userRepository.updateVipStatus(vipId, userId);
    }

    @Transactional
    @Override
    public void changeForgotPassword(String email, String newPass) {
        userRepository.changeForgotPassword(email, newPass);
    }

    @Override
    public int getUserId(String username) {
        return userRepository.getUserId(username);
    }

    @Transactional
    @Override
    public void deleteAccount(int userId) {
        userRepository.deleteAccount(userId);
    }

}
