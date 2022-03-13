package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

    @Query(value = "SELECT * FROM capstone.room_type where hotel_id = :hotelId and status = 1", nativeQuery = true)
    List<RoomType> findRoomTypeByHotelId(@Param("hotelId") int hotelId);

    @Query(value = "SELECT * FROM capstone.room_type where id = :id limit 1", nativeQuery = true)
    RoomType getRoomTypeById(@Param("id") int id);

    @Modifying
    @Query(value = "insert into capstone.room_type(available_rooms, deal_expire, deal_percentage, " +
            "name, number_of_people, price, quantity, hotel_id) " +
            "values (:availableRooms, :dealExpire, :dealPercentage, :name, :numberOfPeople, " +
            ":price, :quantity, :hotelId);",
            nativeQuery = true)
    void addNewRoomType(
            @Param("availableRooms") int availableRooms,
            @Param("dealExpire") Timestamp dealExpire,
            @Param("dealPercentage") int dealPercentage,
            @Param("name") String name,
            @Param("numberOfPeople") int numberOfPeople,
            @Param("price") long price,
            @Param("quantity") int quantity,
            @Param("hotelId") int hotelId);

    @Query(value = "select last_insert_id(id) from capstone.room_type order by last_insert_id(id) desc limit 1;",
            nativeQuery = true)
    Integer getRoomTypeIdJustInsert();

    @Modifying
    @Query(value = "UPDATE capstone.room_type set status = 0 WHERE id = :roomTypeId",
            nativeQuery = true)
    void disableRoomTypeById(@Param("roomTypeId") int roomTypeId);

    @Modifying
    @Query(value = "UPDATE capstone.room_type set status = 1 WHERE id = :roomTypeId",
            nativeQuery = true)
    void enableRoomTypeById(@Param("roomTypeId") int roomTypeId);

    @Modifying
    @Query(value = "create event if not exists event_update_deal_percentage \n" +
            "on schedule \n" +
            "every 1 day\n" +
            "starts current_timestamp \n" +
            "ends current_timestamp + interval 3 month\n" +
            "do\n" +
            "UPDATE capstone.room_type SET deal_percentage = 0 WHERE deal_expire < now();",
            nativeQuery = true)
    void createSQLEventUpdateDealViaDateExpired();

}
