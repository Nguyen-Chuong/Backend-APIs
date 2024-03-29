package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "RoomType")
public class RoomType extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne @JoinColumn(name = "hotelId")
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
