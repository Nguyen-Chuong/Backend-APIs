package com.capstone_project.hbts.dto.Actor;

import com.capstone_project.hbts.dto.VipDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private int status;

    private String phone;

    private String address;

    private int type;

    private String avatar;

    private BigDecimal spend;

    private VipDTO idVip;

}
