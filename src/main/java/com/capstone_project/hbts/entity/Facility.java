package com.capstone_project.hbts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "facility")
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Integer id;

    @Column(name = "name_facility")
    private String name;

    @Column(name = "icon")
    private String icon;

    @ManyToOne @JoinColumn(name = "facility_type_id") @JsonIgnore
    private FacilityType facilityType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "facility") @JsonIgnore
    private Set<RoomFacility> listRoomFacility;

}
