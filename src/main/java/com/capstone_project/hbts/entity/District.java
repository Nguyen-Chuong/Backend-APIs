package com.capstone_project.hbts.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "district")
public class District implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Integer id;

    @Column(name = "name_district") @NotNull
    private String nameDistrict;

    @Column(name = "avatar")
    private String avatar;

    @ManyToOne @JoinColumn(name = "cityId")
    private City city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
    private Set<Hotel> listHotel;

}
