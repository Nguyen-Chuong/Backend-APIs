package com.capstone_project.hbts.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "Users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    private int type; // 0-user, 1-manager, 2-admin

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "spend")
    private BigDecimal spend;

    @Column(name = "status")
    private int status; // 1-active, 0-account deleted

    @ManyToOne @JoinColumn(name = "id_vip")
    private Vip vip;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<UserBooking> listUserBooking;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private Set<Feedback> listSenderFeedback;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
    private Set<Feedback> listResponseFeedback;

    // EAGER loading to get list role when load user outside session in hibernate
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Set<Role> listRole;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Transaction> listTransaction;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Cart> listCart;

}
