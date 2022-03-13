package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<Jwt, Integer> {

    @Query(value = "select * from capstone.jwt where id = :id limit 1", nativeQuery = true)
    Jwt getJwtById(@Param("id") int id);

}
