package com.capstone_project.hbts.dto.Room;

import com.capstone_project.hbts.dto.ImageDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class RoomTypeDTO {

    private Integer id;

    private String name;

    private long price;

    private int numberOfPeople;

    private int quantity;

    private int availableRooms;

    private int dealPercentage;

    private Timestamp dealExpire;

    private Set<ImageDTO> listImage; // not required when update

}
