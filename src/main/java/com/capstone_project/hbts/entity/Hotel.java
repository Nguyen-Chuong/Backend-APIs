package com.capstone_project.hbts.entity;

import com.capstone_project.hbts.audit.Auditable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "hotel")
public class Hotel extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description")
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "star")
    private int star;

    @Column(name = "tax_percentage")
    private int taxPercentage;

    @Column(name = "status")
    private int status; // 1-active, 2-deactivated, 3-pending, 4-banned, 5-denied
    // (if pending: approved -> 1-active, denied -> 5-denied) (if provider disable -> deactivated)
    // if admin banned -> cannot re-active or request again, but if provider disable -> still can re-active

    @ManyToOne @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    private Set<UserBooking> listUserBooking;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    private Set<RoomType> listRoomType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    private Set<Request> listRequest;

}
