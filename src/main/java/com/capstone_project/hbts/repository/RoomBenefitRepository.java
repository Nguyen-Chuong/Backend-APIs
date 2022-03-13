package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.RoomBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBenefitRepository extends JpaRepository<RoomBenefit, Integer> {

    List<RoomBenefit> getAllByRoomTypeId(int roomTypeId);

    @Query(value = "SELECT benefit_id from capstone.room_type_benefit WHERE room_type_id = :roomTypeId",
            nativeQuery = true)
    List<Integer> getListBenefitIds(@Param("roomTypeId") int roomTypeId);

}
