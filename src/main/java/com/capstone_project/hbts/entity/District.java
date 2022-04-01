package com.capstone_project.hbts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
