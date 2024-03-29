package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.FacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacilityTypeRepository extends JpaRepository<FacilityType, Integer> {

    @Query(value = "select * from heroku_4fe5c149618a3f9.facility_type where id = :id limit 1", nativeQuery = true)
    FacilityType getFacilityTypeById(@Param("id") int id);

}
