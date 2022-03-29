package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Jwt;
import com.capstone_project.hbts.repository.JwtRepository;
import com.capstone_project.hbts.request.JwtRequest;
import com.capstone_project.hbts.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtRepository jwtRepository;

    private final ModelMapper modelMapper;

    public JwtServiceImpl(JwtRepository jwtRepository, ModelMapper modelMapper) {
        this.jwtRepository = jwtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveTokenKeyForAdmin(JwtRequest jwtRequest) {
        Jwt jwt = modelMapper.map(jwtRequest, Jwt.class);
        jwtRepository.save(jwt);
    }

    @Override
    public String getTokenKeyForAdmin() {
        return jwtRepository.getJwtById(1).getJwt();
    }

}
