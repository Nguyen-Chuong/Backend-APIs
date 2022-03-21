package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.dto.BankDTO;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.BankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("/api/v1")
public class BankResource {

    private final BankService bankService;

    public BankResource(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * To get all banks
     * @param
     */
    @GetMapping("/list-bank")
    public ResponseEntity<?> getListBank() {
        log.info("REST request to get all bank");

        try {
            List<BankDTO> bankDTOList = bankService.getAllBanks();
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, bankDTOList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
