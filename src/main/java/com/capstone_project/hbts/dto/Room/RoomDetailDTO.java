package com.capstone_project.hbts.dto.Room;

import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.dto.Facility.ObjectFacility;
import com.capstone_project.hbts.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailDTO {

    private Integer id;

    private String name;

    private long price;

    private int numberOfPeople;

    private int quantity;

    private int availableRooms;

    private int dealPercentage;

    private Timestamp dealExpire;

    private Set<ImageDTO> listImage;

    private List<ObjectFacility> listFacility;

    private List<ObjectBenefit> listBenefit;

}
