package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.RoomBenefit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomBenefitRepository extends CrudRepository<RoomBenefit, Integer> {

    List<RoomBenefit> getAllByRoomTypeId(int roomTypeId);

    @Query(value = "SELECT r.benefit.id from RoomBenefit r WHERE r.roomType.id = :roomTypeId")
    List<Integer> getListBenefitIds(@Param("roomTypeId") int roomTypeId);

}
