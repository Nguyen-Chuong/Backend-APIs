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
@Table(name = "Review")
public class Review extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "service")
    private float service; // type

    @Column(name = "value_money")
    private float valueForMoney; // type

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_detail")
    private String reviewDetail;

    @Column(name = "cleanliness")
    private float cleanliness; // type

    @Column(name = "location")
    private float location; // type

    @Column(name = "facilities")
    private float facilities; // type

    @Column(name = "total")
    private float total; // average score

    @Column(name = "review_date")
    private Timestamp reviewDate;

    @ManyToOne @JoinColumn(name = "userBooking_Id")
    private UserBooking userBooking;

}

