package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT count(i.id) FROM Image i WHERE i.roomType.id = :roomTypeId")
    Integer getTotalNumberOfRoomImage(@Param("roomTypeId") int roomTypeId);

    @Query(value = "SELECT * FROM heroku_4fe5c149618a3f9.image WHERE id = :id limit 1",
            nativeQuery = true)
    Image getImageById(@Param("id") int id);

}
