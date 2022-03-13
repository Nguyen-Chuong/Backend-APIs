package com.capstone_project.hbts.dto.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class RequestDTO {

    private Integer id;

    private HotelRequestDTO hotel;

    private Timestamp requestDate;

    private ProviderRequestDTO provider;

    private int status;

}
