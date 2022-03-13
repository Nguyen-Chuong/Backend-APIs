package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.Hotel.HotelDTO;
import com.capstone_project.hbts.dto.Hotel.HotelDetailDTO;
import com.capstone_project.hbts.request.HotelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface HotelService {

    /**
     * search hotel for user n guest
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

    /**
     * get page hotel dto for admin
     * @param status
     * @param pageable
     * @return
     */
    Page<HotelDTO> getAllHotels(int status, Pageable pageable);

    /**
     * get detail hotel by id
     * @param hotelId
     * @return
     */
    HotelDetailDTO getDetailHotelById(int hotelId);

    /**
     * ban hotel by id for admin
     * @param hotelId
     * @return
     */
    void banHotelByHotelId(int hotelId);

    /**
     * ban hotel by provider id
     * @param providerId
     * @return
     */
    void banHotelByProviderId(int providerId);

    /**
     * view list hotel for provider
     * @param providerId
     * @return
     */
    List<HotelDTO> viewListHotelByProviderId(int providerId);

    /**
     * disable hotel for provider
     * @param hotelId
     * @return
     */
    void disableHotel(int hotelId);

    /**
     * enable hotel for provider
     * @param hotelId
     * @return
     */
    void enableHotel(int hotelId);

    /**
     * get hotel by id
     * @param hotelId
     * @return
     */
    HotelDTO getHotelById(int hotelId);

    /**
     * add a hotel by provider
     * @param hotelRequest
     * @return
     */
    void addHotelByProvider(HotelRequest hotelRequest);

    /**
     * Get hotel id just insert
     * @return
     */
    Integer getHotelIdJustInsert();

    /**
     * view a hotel status
     * @param hotelId
     * @return
     */
    Integer viewHotelStatus(int hotelId);

    /**
     * update hotel for provider
     * @param hotelDTO
     * @return
     */
    void updateHotel(HotelDTO hotelDTO);

    /**
     * check if hotel had rooms or not
     * @param hotelId
     * @return
     */
    boolean isHotelHadRoom(int hotelId);

}
