package com.capstone_project.hbts.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "facility_type")
public class FacilityType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_facility_type")
    private String name;

    @Column(name = "icon")
    private String icon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facilityType")
    private Set<Facility> listFacility;

}
