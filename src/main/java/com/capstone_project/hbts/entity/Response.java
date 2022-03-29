package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "Response")
public class Response extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "modify_date")
    private Timestamp modifyDate;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "admin_id")
    private int adminId;

    @ManyToOne @JoinColumn(name = "feedback_id")
    private Feedback feedback;

}
