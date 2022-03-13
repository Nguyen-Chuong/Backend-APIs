package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.RoomFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomFacilityRepository extends JpaRepository<RoomFacility, Integer> {

    List<RoomFacility> getAllByRoomTypeId(int roomTypeId);

    @Query(value = "SELECT facility_id from capstone.room_facility WHERE room_type_id = :roomTypeId",
            nativeQuery = true)
    List<Integer> getListFacilityIds(@Param("roomTypeId") int roomTypeId);

}
