package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "Feedback")
public class Feedback extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // type 1: complain, type 2: refund, type 3: other, v v
    @Column(name = "type")
    private int type;

    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "message")
    private String message;

    @Column(name = "modify_date")
    private Timestamp modifyDate;

    @Column(name = "is_completed") // that admin/manager start to process
    private int isProcessed;

    @ManyToOne @JoinColumn(name = "sender_id")
    private Users sender;

    @ManyToOne @JoinColumn(name = "receiver_id")
    private Users receiver;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feedback")
    private Set<Response> listResponse;

}
