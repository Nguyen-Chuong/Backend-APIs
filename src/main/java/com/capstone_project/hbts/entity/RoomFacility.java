package com.capstone_project.hbts.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "room_facility")
public class RoomFacility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    private Integer id;

    @ManyToOne @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @ManyToOne @JoinColumn(name = "facility_id")
    private Facility facility;

}
