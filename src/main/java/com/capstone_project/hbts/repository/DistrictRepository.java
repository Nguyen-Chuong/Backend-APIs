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

    @Query(value = "SELECT * from capstone.district WHERE name_district like lower(concat('%',:text,'%')) ",
            nativeQuery = true)
    List<District> searchDistrict(@Param("text") String text);

    @Query(value = "select new com.capstone_project.hbts.dto.Location.CityDistrict(district.id, " +
            "district.nameDistrict, city.nameCity) from District " +
            "as district join City as city on district.city.id = city.id " +
            "where district.nameDistrict like lower(concat('%',:text,'%')) " +
            "or city.nameCity like lower(concat('%',:text,'%')) ")
    List<CityDistrict> searchDistrictCity(@Param("text") String text);

    List<District> getAllByCityIdOrderByNameDistrictAsc(int cityId);

}
