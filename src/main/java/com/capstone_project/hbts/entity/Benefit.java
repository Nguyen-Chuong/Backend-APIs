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
@Table(name = "benefit")
public class Benefit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @ManyToOne @JoinColumn(name = "benefit_type_id") @JsonIgnore
    private BenefitType benefitType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "benefit")
    private Set<RoomBenefit> listRoomBenefit;

}
