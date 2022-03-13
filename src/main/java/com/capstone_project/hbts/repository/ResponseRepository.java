package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Integer> {

    @Modifying
    @Query(value = "insert into capstone.response(admin_id, message, modify_date, user_id, feedback_id) " +
            "values (:adminId, :message, :modifyDate, :userId, :feedbackId);",
            nativeQuery = true)
    void sendResponseFromFeedback(
            @Param("adminId") int adminId,
            @Param("message") String message,
            @Param("modifyDate") Timestamp modifyDate,
            @Param("userId") int userId,
            @Param("feedbackId") int feedbackId);

    @Query(value = "SELECT admin_id FROM capstone.response where feedback_id = :feedbackId " +
            "Order by modify_date desc limit 1",
            nativeQuery = true)
    Integer getAdminId(@Param("feedbackId") int feedbackId);

    List<Response> findAllByFeedback_IdOrderByModifyDateAsc(int feedbackId);

}
