package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    Page<Feedback> findAllByOrderByModifyDateDesc(Pageable pageable);

    @Query(value = "SELECT f from Feedback f WHERE f.sender.id = :userId")
    List<Feedback> getUserFeedback(@Param("userId") int userId);

    @Query(value = "SELECT * from heroku_4fe5c149618a3f9.feedback WHERE id = :feedbackId limit 1", nativeQuery = true)
    Feedback getFeedbackById(@Param("feedbackId") int feedbackId);

    @Modifying
    @Query(value = "UPDATE heroku_4fe5c149618a3f9.feedback set receiver_id = :adminId, is_completed = 1 WHERE id = :feedbackId", nativeQuery = true)
    void updateFeedbackReceiver(@Param("feedbackId") int feedbackId, @Param("adminId") int adminId);

}
