package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "user_booking")
public class UserBooking extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "checkIn")
    private Timestamp checkIn;

    @Column(name = "checkOut")
    private Timestamp checkOut;

    @Column(name = "status")
    private int status; // 1-upcoming, 2-completed, 3-cancelled

    @Column(name = "review_status")
    private int reviewStatus; // 0-not yet, 1-reviewed

    @Column(name = "booking_date")
    private Timestamp bookingDate;

    @Column(name = "booked_quantity")
    private int bookedQuantity; // number of people booked = total booking detail, process on FE

    @Column(name = "other_requirement")
    private String otherRequirement;

    @Column(name = "type")
    private int type; // 0 - default, 1 - cod , 2 - payment

    @ManyToOne @JsonIgnore @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBooking")
    private Set<Review> listReview;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBooking") @JsonIgnore
    private Set<UserBookingDetail> listUserBookingDetail;

}
