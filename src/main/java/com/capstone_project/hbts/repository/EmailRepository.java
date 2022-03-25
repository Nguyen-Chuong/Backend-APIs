package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {
    List<Email> findAllBySubject(String subject);
}
