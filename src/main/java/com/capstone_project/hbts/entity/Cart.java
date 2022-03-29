package com.capstone_project.hbts.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "Cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Integer id;

    @Column(name = "room_type_id")
    private int roomTypeId;

    @Column(name = "hotel_id")
    private int hotelId;

    @Column(name = "quantity")
    private int quantity; // room quantity

    @Column(name = "booked_quantity")
    private int bookedQuantity; // number of people

    @Column(name = "date_in")
    private Date dateIn;

    @Column(name = "date_out")
    private Date dateOut;

    @ManyToOne @JoinColumn(name = "user_id")
    private Users users;

}
