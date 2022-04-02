package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.dto.location.CityDistrict;
import com.capstone_project.hbts.dto.location.DistrictDTO;
import com.capstone_project.hbts.dto.location.DistrictSearchDTO;
import com.capstone_project.hbts.dto.location.ResultSearch;
import com.capstone_project.hbts.entity.District;
import com.capstone_project.hbts.entity.Hotel;
import com.capstone_project.hbts.repository.DistrictRepository;
import com.capstone_project.hbts.service.DistrictService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;

    private final ModelMapper modelMapper;

    public DistrictServiceImpl(DistrictRepository districtRepository, ModelMapper modelMapper) {
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ResultSearch> searchDistrictCity(String text) {
        // get city & district searched from db
        List<CityDistrict> list = districtRepository.searchDistrictCity(text);
        // new list result search
        List<ResultSearch> listResultSearch = new ArrayList<>();
        // new object result search from city and district searched
        for (CityDistrict cityDistrict : list) {
            ResultSearch resultSearch = new ResultSearch(cityDistrict.getId(), cityDistrict.getDistrictName()
                    + " District, " + cityDistrict.getCityName());
            listResultSearch.add(resultSearch);
        }
        return listResultSearch;
    }

    @Override
    public List<DistrictSearchDTO> getAllDistrictInCity(int cityId) {
        return districtRepository.getAllByCityIdOrderByNameDistrictAsc(cityId).stream()
                .map(item -> modelMapper.map(item, DistrictSearchDTO.class)).collect(Collectors.toList());
    }

    // count total booking of an district
    public long countTotalBookingForEachDistrict(District district){
        Set<Hotel> hotelSet = district.getListHotel();
        long total = 0;
        for(Hotel hotel : hotelSet){
            total = total + hotel.getListUserBooking().size();
        }
        return total;
    }

    @Override
    public List<DistrictDTO> getTopHotLocation(int limit) {
        // get list top hot district id
        List<Integer> districtIds = districtRepository.getTopHotLocation(limit);
        // get district from list ids
        List<District> districtList =  districtRepository.findAllById(districtIds);
        // convert to dto
        List<DistrictDTO> districtDTOList = districtList.stream().map(item -> modelMapper.map(item, DistrictDTO.class))
                .collect(Collectors.toList());
        // set number booking property
        for(int i = 0; i < districtDTOList.size(); i++){
            districtDTOList.get(i).setTotalBooking(countTotalBookingForEachDistrict(districtList.get(i)));
        }
        return districtDTOList;
    }

}
