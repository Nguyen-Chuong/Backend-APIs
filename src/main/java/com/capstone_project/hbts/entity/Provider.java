package com.capstone_project.hbts.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "Provider")
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @Size(max = 30)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "providerName")
    private String providerName;

    @Column(name = "status")
    private int status;  // 1-active, 0-banned

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
    private Set<Hotel> listHotel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
    private Set<Request> listRequest;

}
