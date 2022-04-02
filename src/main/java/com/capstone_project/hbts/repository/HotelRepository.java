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

    @Query(value = "SELECT h.status FROM Hotel h WHERE h.id = :hotelId")
    Integer viewHotelStatus(@Param("hotelId") int hotelId);

    @Query(value = "select last_insert_id(id) from heroku_4fe5c149618a3f9.hotel order by last_insert_id(id) desc limit 1;", nativeQuery = true)
    Integer getHotelIdJustInsert();

}
