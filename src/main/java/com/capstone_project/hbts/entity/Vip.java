package com.capstone_project.hbts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "Vip")
public class Vip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_vip")
    private String nameVip;

    @Column(name = "range_start")
    private int rangeStart;

    @Column(name = "range_end")
    private int rangeEnd;

    @Column(name = "discount")
    private int discount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vip") @JsonIgnore
    private Set<Users> listUsers;

}
