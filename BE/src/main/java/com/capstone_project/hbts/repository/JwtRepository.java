package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Integer> {

}
