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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Provider")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Email
    @Size(min = 5, max = 50, message = "{casa.nomatch.size}")
    private String email;

    @Column(name = "phone")
    @Size(min = 5, max = 20, message = "{casa.nomatch.size}")
    @Pattern(regexp = "(09|03|01|05|08)+([0-9]{7,10})\\b")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
    private Set<Hotel> listHotel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
    private Set<Request> listRequest;

}
