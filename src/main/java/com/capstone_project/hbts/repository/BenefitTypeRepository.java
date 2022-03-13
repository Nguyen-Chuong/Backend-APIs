package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.BenefitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitTypeRepository extends JpaRepository<BenefitType, Integer> {

    @Query(value = "select * from capstone.benefit_type where id = :id limit 1", nativeQuery = true)
    BenefitType getBenefitTypeById(@Param("id") int id);

}
