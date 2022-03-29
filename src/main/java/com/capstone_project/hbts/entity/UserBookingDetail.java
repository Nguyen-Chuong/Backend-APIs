package com.capstone_project.hbts.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "UserBookingDetail")
public class UserBookingDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Integer id;

    @Column(name = "paid")
    private BigDecimal paid; // this is the price that user have to pay for this room type

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne @JoinColumn(name = "booking_id")
    private UserBooking userBooking;

    @ManyToOne @JoinColumn(name = "room_type_id")
    private RoomType roomType;

}
