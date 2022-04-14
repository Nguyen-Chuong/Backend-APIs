package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
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
    private int bookedQuantity;

    @Column(name = "other_requirement")
    private String otherRequirement;

    @Column(name = "type")
    private int type; // 0 - default, 1 - cod , 2 - payment

    @Column(name = "has_coupon")
    private int hasCoupon;

    @ManyToOne @JsonIgnore @JoinColumn(name = "userId")
    private Users users;

    @ManyToOne @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBooking")
    private Set<Review> listReview;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userBooking") @JsonIgnore
    private Set<UserBookingDetail> listUserBookingDetail;

}
