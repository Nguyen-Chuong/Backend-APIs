package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends CrudRepository<Image, Integer> {

    @Query(value = "SELECT count(i.id) FROM Image i WHERE i.roomType.id = :roomTypeId")
    Integer getTotalNumberOfRoomImage(@Param("roomTypeId") int roomTypeId);

}
