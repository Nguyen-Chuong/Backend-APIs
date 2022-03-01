package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query(value = "select * from capstone.hotel where district_id = :districtId", nativeQuery = true)
    Page<Hotel> searchHotelByDistrict(
            @Param("districtId") int districtId,
            Pageable pageable);

    List<Hotel> findAllByStatus(int status);

}
