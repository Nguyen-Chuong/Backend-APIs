package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Modifying
    @Query(value = "insert into heroku_4fe5c149618a3f9.feedback(type, sender_id, message, modify_date, is_completed) " +
            "values (:type, :senderId, :message, :modifyDate, :isCompleted);",
            nativeQuery = true)
    void sendFeedback(
            @Param("type") int type,
            @Param("senderId") int senderId,
            @Param("message") String message,
            @Param("modifyDate") Timestamp modifyDate,
            @Param("isCompleted") int isCompleted);

    Page<Feedback> findAllByOrderByModifyDateDesc(Pageable pageable);

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.feedback WHERE sender_id = :userId",
            nativeQuery = true)
    List<Feedback> getUserFeedback(@Param("userId") int userId);

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.feedback WHERE id = :feedbackId limit 1",
            nativeQuery = true)
    Feedback getFeedbackById(@Param("feedbackId") int feedbackId);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.feedback set receiver_id = :adminId, is_completed = 1 WHERE id = :feedbackId",
            nativeQuery = true)
    void updateFeedbackReceiver(@Param("feedbackId") int feedbackId,
                                @Param("adminId") int adminId);

}
