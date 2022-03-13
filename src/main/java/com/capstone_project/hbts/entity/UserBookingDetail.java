package com.capstone_project.hbts.entity;

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
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "UserBookingDetail")
public class UserBookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "paid")
    private BigDecimal paid; // this is the price that user have to pay for this room type

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private UserBooking userBooking;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

}
