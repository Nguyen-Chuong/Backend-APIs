package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @Query(value = "SELECT r FROM RoomType r where r.hotel.id = :hotelId and r.status = 1")
    List<RoomType> findRoomTypeByHotelId(@Param("hotelId") int hotelId);

    @Query(value = "SELECT * FROM heroku_4fe5c149618a3f9.room_type where id = :id limit 1", nativeQuery = true)
    RoomType getRoomTypeById(@Param("id") int id);

    @Query(value = "select last_insert_id(id) from heroku_4fe5c149618a3f9.room_type order by last_insert_id(id) desc limit 1;", nativeQuery = true)
    Integer getRoomTypeIdJustInsert();

    @Modifying
    @Query(value = "UPDATE RoomType r set r.status = 0 WHERE r.id = :roomTypeId")
    void disableRoomTypeById(@Param("roomTypeId") int roomTypeId);

    @Modifying
    @Query(value = "UPDATE RoomType r set r.status = 1 WHERE r.id = :roomTypeId")
    void enableRoomTypeById(@Param("roomTypeId") int roomTypeId);

    @Modifying
    @Query(value = "create event if not exists event_update_deal_percentage on schedule every 1 day starts current_timestamp \n" +
            "ends current_timestamp + interval 3 month do UPDATE heroku_4fe5c149618a3f9.room_type SET deal_percentage = 0 " +
            "WHERE deal_expire < now() ", nativeQuery = true)
    void createSQLEventUpdateDealViaDateExpired();

}
