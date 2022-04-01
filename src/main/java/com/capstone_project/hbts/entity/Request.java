package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
