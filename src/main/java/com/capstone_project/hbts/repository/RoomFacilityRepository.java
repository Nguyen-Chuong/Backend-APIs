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

    @Query(value = "SELECT r.facility.id from RoomFacility r WHERE r.roomType.id = :roomTypeId")
    List<Integer> getListFacilityIds(@Param("roomTypeId") int roomTypeId);

}
