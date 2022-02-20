package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.HotelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;

public interface HotelService {

    /**
     * @param districtId
     * @param dateIn
     * @param dateOut
     * @param numberOfPeople
     * @param numberOfRoom
     * @param pageable
     * @return
     */
    Page<HotelDTO> searchHotel(int districtId, Date dateIn, Date dateOut,
                               int numberOfPeople, int numberOfRoom, Pageable pageable);

}
