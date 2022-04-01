package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    // find all hotel active
    @Query(value = "select h from Hotel h where h.district.id = :districtId and h.status = 1 ")
    Page<Hotel> searchHotelByDistrict(@Param("districtId") int districtId, Pageable pageable);

    @Query(value = "select h from Hotel h where h.district.id = :districtId and h.status = 1 ")
    List<Hotel> getTotalHotelWithoutPaging(@Param("districtId") int districtId);

    Page<Hotel> findAllByStatus(int status, Pageable pageable);

    @Query(value = "select * from heroku_4fe5c149618a3f9.hotel where id = :id limit 1", nativeQuery = true)
    Hotel getHotelById(@Param("id") int id);

    @Query(value = "select h from Hotel h")
    Page<Hotel> findAllHotel(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE Hotel h set h.status = :status WHERE h.id = :hotelId")
    void updateHotelStatus(@Param("hotelId") int hotelId, @Param("status") int status);

    @Modifying
    @Query(value = "UPDATE Hotel h set h.status = 4 WHERE h.provider.id = :providerId")
    void banHotelByProviderId(@Param("providerId") int providerId);

    List<Hotel> getAllByProviderId(int providerId);

    @Modifying
    @Query(value = "insert into heroku_4fe5c149618a3f9.hotel(address, avatar, description, email, name, phone, status, district_id, provider_id, star, tax_percentage) " +
            "values (:address, :avatar, :description, :email, :name, :phone, :status, :districtId, :providerId, :star, :taxPercentage)", nativeQuery = true)
    void addNewHotel(@Param("address") String address, @Param("avatar") String avatar, @Param("description") String description, @Param("email") String email,
                     @Param("name") String name, @Param("phone") String phone, @Param("status") int status, @Param("districtId") int districtId,
                     @Param("providerId") int providerId, @Param("star") int star, @Param("taxPercentage") int taxPercentage);

    @Query(value = "SELECT h.status FROM Hotel h WHERE h.id = :hotelId")
    Integer viewHotelStatus(@Param("hotelId") int hotelId);

    @Query(value = "select last_insert_id(id) from heroku_4fe5c149618a3f9.hotel order by last_insert_id(id) desc limit 1;", nativeQuery = true)
    Integer getHotelIdJustInsert();

}
