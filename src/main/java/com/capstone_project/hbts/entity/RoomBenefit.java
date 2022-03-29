package com.capstone_project.hbts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "roomType_benefit")
public class RoomBenefit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Integer id;

    @ManyToOne @JsonIgnore @JoinColumn(name = "benefit_id")
    private Benefit benefit;

    @ManyToOne @JsonIgnore @JoinColumn(name = "roomType_id")
    private RoomType roomType;

}
