package com.capstone_project.hbts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RoomType")
public class RoomType {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private long price;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "availableRooms")
    private int availableRooms;

    @Column(name = "dealPercentage")
    private int dealPercentage;

    @Column(name = "dealExpire")
    private Timestamp dealExpire;

    @Column(name = "status")
    private int status; // 0-deactivate 1-active

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomType")
    private Set<UserBookingDetail> listUserBookingDetail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomType")
    private Set<Image> listImage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomType")
    private Set<RoomFacility> listRoomFacility;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "roomType")
    private Set<RoomBenefit> listRoomBenefit;

}
