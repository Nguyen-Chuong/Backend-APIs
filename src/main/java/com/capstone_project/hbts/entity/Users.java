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
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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
    private int type; // 0-user, 1-manager, 2-admin

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "spend")
    private BigDecimal spend;

    @Column(name = "status")
    private int status; // 1-active, 0-account deleted

    @ManyToOne
    @JoinColumn(name = "id_vip")
    private Vip idVip;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<UserBooking> listUserBooking;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private Set<Feedback> listSenderFeedback;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver")
    private Set<Feedback> listResponseFeedback;

    // EAGER loading to get list role when load user outside session in hibernate
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Set<Role> listRole;

}
