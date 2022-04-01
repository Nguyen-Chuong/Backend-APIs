package com.capstone_project.hbts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
