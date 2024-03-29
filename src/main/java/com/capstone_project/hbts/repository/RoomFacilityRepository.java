package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.RoomFacility;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomFacilityRepository extends CrudRepository<RoomFacility, Integer> {

    List<RoomFacility> getAllByRoomTypeId(int roomTypeId);

    @Query(value = "SELECT r.facility.id from RoomFacility r WHERE r.roomType.id = :roomTypeId")
    List<Integer> getListFacilityIds(@Param("roomTypeId") int roomTypeId);

}
