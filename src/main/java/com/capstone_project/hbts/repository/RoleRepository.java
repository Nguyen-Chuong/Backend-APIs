package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    @Modifying
    @Query(value = "UPDATE Role r SET r.name = 'ROLE_USER' WHERE r.users.id = :userId")
    void deleteManagerRole(@Param("userId") int userId);

}
