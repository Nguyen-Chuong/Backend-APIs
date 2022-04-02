package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Response;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ResponseRepository extends CrudRepository<Response, Integer> {

    @Modifying
    @Query(value = "insert into heroku_4fe5c149618a3f9.response(admin_id, message, modify_date, user_id, feedback_id, send_by) " +
            "values (:adminId, :message, :modifyDate, :userId, :feedbackId, :sendBy)", nativeQuery = true)
    void sendResponseFromFeedback(@Param("adminId") int adminId, @Param("message") String message, @Param("sendBy") int sendBy,
                                  @Param("modifyDate") Timestamp modifyDate, @Param("userId") int userId, @Param("feedbackId") int feedbackId);

    @Query(value = "SELECT admin_id FROM heroku_4fe5c149618a3f9.response where feedback_id = :feedbackId Order by modify_date desc limit 1", nativeQuery = true)
    int getAdminId(@Param("feedbackId") int feedbackId);

    List<Response> findAllByFeedback_IdOrderByModifyDateAsc(int feedbackId);

}
