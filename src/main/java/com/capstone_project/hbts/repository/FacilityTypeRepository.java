package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityTypeRepository extends JpaRepository<FacilityType, Integer> {

    @Query(value = "select * from capstone.facility_type where id = :id limit 1", nativeQuery = true)
    FacilityType getFacilityTypeById(@Param("id") int id);

}
