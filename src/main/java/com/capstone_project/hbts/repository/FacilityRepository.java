package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {
    // crud facility (for admin or provider)
}
