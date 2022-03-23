package com.capstone_project.hbts.dto.Request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class RequestDTO {

    private Integer id;

    private HotelRequestDTO hotel;

    private Timestamp requestDate;

    private ProviderRequestDTO provider;

    private int status;

}
