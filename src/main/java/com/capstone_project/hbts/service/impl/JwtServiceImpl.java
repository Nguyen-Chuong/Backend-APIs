package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Jwt;
import com.capstone_project.hbts.repository.JwtRepository;
import com.capstone_project.hbts.request.JwtRequest;
import com.capstone_project.hbts.service.JwtService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JwtServiceImpl implements JwtService {

    private final JwtRepository jwtRepository;

    private final ModelMapper modelMapper;

    public JwtServiceImpl(JwtRepository jwtRepository, ModelMapper modelMapper) {
        this.jwtRepository = jwtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveTokenKeyForAdmin(JwtRequest jwtRequest) {
        log.info("Request to save an jwt token to db for admin");
        Jwt jwt = modelMapper.map(jwtRequest, Jwt.class);
        jwtRepository.save(jwt);
    }

    @Override
    public String getTokenKeyForAdmin() {
        log.info("Request to get a token for admin to verify");
        return jwtRepository.getJwtById(1).getJwt();
    }

}
