package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findAllBySubject(String subject);
}
