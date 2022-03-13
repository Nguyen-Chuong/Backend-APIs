package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {
    // crud benefit (for admin or provider)
}
