package com.capstone_project.hbts.service;

import com.capstone_project.hbts.dto.hotel.HotelDTO;
import com.capstone_project.hbts.dto.hotel.HotelDetailDTO;
import com.capstone_project.hbts.dto.RatingDTO;
import com.capstone_project.hbts.request.HotelRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface HotelService {

    /**
     * search hotel for user n guest
     */
    Page<HotelDTO> searchHotel(int districtId, Date dateIn, Date dateOut, int numberOfPeople, int numberOfRoom, Pageable pageable);

    /**
     * get page hotel dto for admin
     */
    Page<HotelDTO> getAllHotels(int status, Pageable pageable);

    /**
     * get detail hotel by id
     */
    HotelDetailDTO getDetailHotelById(int hotelId);

    /**
     * ban hotel by id for admin
     */
    void banHotelByHotelId(int hotelId);

    /**
     * ban hotel by provider id
     */
    void banHotelByProviderId(int providerId);

    /**
     * view list hotel for provider
     */
    List<HotelDTO> viewListHotelByProviderId(int providerId);

    /**
     * disable hotel for provider
     */
    void disableHotel(int hotelId);

    /**
     * enable hotel for provider
     */
    void enableHotel(int hotelId);

    /**
     * get hotel by id
     */
    HotelDTO getHotelById(int hotelId);

    /**
     * add a hotel by provider
     */
    void addHotelByProvider(HotelRequest hotelRequest);

    /**
     * Get hotel id just insert
     */
    Integer getHotelIdJustInsert();

    /**
     * view a hotel status
     */
    Integer viewHotelStatus(int hotelId);

    /**
     * update hotel for provider
     */
    void updateHotel(HotelDTO hotelDTO);

    /**
     * check if hotel had rooms or not
     */
    boolean isHotelHadRoom(int hotelId);

    /**
     * get average user's rating about hotel
     */
    RatingDTO getRatingByHotel(int hotelId);

    /**
     * get top hot hotel
     */
    List<HotelDTO> getTopHotHotel(int top);

    /**
     * get size hotel no paging
     */
    int getSizeNoPaging(int districtId, int numberOfPeople, int numberOfRoom);

}
