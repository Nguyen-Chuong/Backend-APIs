package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    // find all hotel active
    @Query(value = "select * from heroku_4fe5c149618a3f9.hotel where district_id = :districtId and status = 1 ",
            nativeQuery = true)
    Page<Hotel> searchHotelByDistrict(@Param("districtId") int districtId,
                                      Pageable pageable);

    Page<Hotel> findAllByStatus(int status, Pageable pageable);

    @Query(value = "select * from heroku_4fe5c149618a3f9.hotel where id = :id limit 1", nativeQuery = true)
    Hotel getHotelById(@Param("id") int id);

    @Query(value = "select * from heroku_4fe5c149618a3f9.hotel", nativeQuery = true)
    Page<Hotel> findAllHotel(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.hotel set status = 4 WHERE id = :hotelId",
            nativeQuery = true)
    void banHotelById(@Param("hotelId") int hotelId);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.hotel set status = 4 WHERE provider_id = :providerId",
            nativeQuery = true)
    void banHotelByProviderId(@Param("providerId") int providerId);

    List<Hotel> getAllByProviderId(int providerId);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.hotel set status = 2 WHERE id = :hotelId",
            nativeQuery = true)
    void disableHotel(@Param("hotelId") int hotelId);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.hotel set status = 1 WHERE id = :hotelId",
            nativeQuery = true)
    void enableHotel(@Param("hotelId") int hotelId);

    @Modifying
    @Query(value = "insert into heroku_4fe5c149618a3f9.hotel(address, avatar, description, email, " +
            "name, phone, status, district_id, provider_id, star, tax_percentage) " +
            "values (:address, :avatar, :description, :email, :name, " +
            ":phone, :status, :districtId, :providerId, :star, :taxPercentage)",
            nativeQuery = true)
    void addNewHotel(
            @Param("address") String address,
            @Param("avatar") String avatar,
            @Param("description") String description,
            @Param("email") String email,
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("status") int status,
            @Param("districtId") int districtId,
            @Param("providerId") int providerId,
            @Param("star") int star,
            @Param("taxPercentage") int taxPercentage);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.hotel set status = 5 WHERE id = :hotelId",
            nativeQuery = true)
    void denyHotelById(@Param("hotelId") int hotelId);

    @Query(value = "SELECT status FROM heroku_4fe5c149618a3f9.hotel WHERE id = :hotelId",
            nativeQuery = true)
    Integer viewHotelStatus(@Param("hotelId") int hotelId);

    @Query(value = "select last_insert_id(id) from heroku_4fe5c149618a3f9.hotel order by " +
            "last_insert_id(id) desc limit 1;",
            nativeQuery = true)
    Integer getHotelIdJustInsert();

}
