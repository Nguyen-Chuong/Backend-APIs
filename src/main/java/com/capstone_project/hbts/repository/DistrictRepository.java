package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.dto.Location.CityDistrict;
import com.capstone_project.hbts.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {

    @Query(value = "SELECT d from District d WHERE d.nameDistrict like lower(concat('%',:text,'%')) ")
    List<District> searchDistrict(@Param("text") String text);

    @Query(value = "select new com.capstone_project.hbts.dto.Location.CityDistrict(district.id, " +
            "district.nameDistrict, city.nameCity) from District " +
            "as district join City as city on district.city.id = city.id " +
            "where district.nameDistrict like lower(concat('%',:text,'%')) " +
            "or city.nameCity like lower(concat('%',:text,'%')) ")
    List<CityDistrict> searchDistrictCity(@Param("text") String text);

    List<District> getAllByCityIdOrderByNameDistrictAsc(int cityId);

    @Query(value = "select district_id from (select count(user_booking.id) as booking_number, " +
            "user_booking.hotel_id, hotel.district_id from heroku_4fe5c149618a3f9.user_booking " +
            "join heroku_4fe5c149618a3f9.hotel on user_booking.hotel_id = hotel.id " +
            "group by user_booking.hotel_id) raw_data group by district_id " +
            "order by sum(booking_number) desc limit :limit", nativeQuery = true)
    List<Integer> getTopHotLocation(@Param("limit") int limit);

}
