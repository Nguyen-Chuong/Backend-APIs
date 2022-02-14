package com.capstone_project.hbts.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @Size(max = 30)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    @Email
    @Size(min = 5, max = 50, message = "{casa.nomatch.size}")
    private String email;

    @Column(name = "phone")
    @Size(min = 5, max = 20, message = "{casa.nomatch.size}")
    @Pattern(regexp = "(09|03|01|05|08)+([0-9]{7,10})\\b")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    private int type;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "spend")
    private BigDecimal spend;

    @Column(name = "otp")
    private int otp;

    @ManyToOne
    @JoinColumn(name = "id_vip")
    private Vip idVip;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<UserBooking> listUserBooking = new HashSet<UserBooking>();

}
