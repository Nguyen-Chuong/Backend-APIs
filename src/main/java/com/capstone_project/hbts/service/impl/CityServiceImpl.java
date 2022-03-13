package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Location.CityDTO;
import com.capstone_project.hbts.repository.CityRepository;
import com.capstone_project.hbts.service.CityService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final ModelMapper modelMapper;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CityDTO> viewAllCity() {
        log.info("Request to view all city");
        return cityRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, CityDTO.class))
                .collect(Collectors.toList());
    }

}
