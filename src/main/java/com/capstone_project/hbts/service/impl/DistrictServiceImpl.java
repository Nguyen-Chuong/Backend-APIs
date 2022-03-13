package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.Location.CityDistrict;
import com.capstone_project.hbts.dto.Location.DistrictSearchDTO;
import com.capstone_project.hbts.dto.Location.ResultSearch;
import com.capstone_project.hbts.repository.DistrictRepository;
import com.capstone_project.hbts.service.DistrictService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    private final ModelMapper modelMapper;

    public DistrictServiceImpl(DistrictRepository districtRepository, ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<DistrictSearchDTO> searchDistrict(String text) {
        log.info("Request to search district by text");
        return districtRepository.searchDistrict(text)
                .stream()
                .map(item -> modelMapper.map(item, DistrictSearchDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultSearch> searchDistrictCity(String text) {
        log.info("Request to search district and city by text");
        // get city & district searched from db
        List<CityDistrict> list = districtRepository.searchDistrictCity(text);
        // new list result search
        List<ResultSearch> listResultSearch = new ArrayList<>();
        // new object result search from city and district searched
        for (CityDistrict cityDistrict : list) {
            ResultSearch resultSearch = new ResultSearch(cityDistrict.getId(), // district id
                    cityDistrict.getDistrictName() + " District, " + cityDistrict.getCityName());
            listResultSearch.add(resultSearch);
        }
        return listResultSearch;
    }

    @Override
    public List<DistrictSearchDTO> getAllDistrictInCity(int cityId) {
        log.info("Request to get all district in city");
        return districtRepository.getAllByCityIdOrderByNameDistrictAsc(cityId)
                .stream()
                .map(item -> modelMapper.map(item, DistrictSearchDTO.class))
                .collect(Collectors.toList());
    }

}
