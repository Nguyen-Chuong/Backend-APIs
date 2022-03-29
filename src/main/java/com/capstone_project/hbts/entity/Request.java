package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "request")
public class Request extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_date")
    private Timestamp requestDate;

    @Column(name = "status")
    private int status; // 1-pending, 2-accepted, 3-denied, 4-cancelled

    @ManyToOne @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne @JoinColumn(name = "provider_id")
    private Provider provider;

}
