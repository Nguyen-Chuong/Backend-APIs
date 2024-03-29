package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.dto.location.CityDTO;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.CityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1")
public class CityResource {

    private final CityService cityService;

    public CityResource(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * @apiNote to get all city
     */
    @GetMapping("/get-city")
    public ResponseEntity<?> getAllCity() {
        log.info("REST request to get all city");
        try {
            List<CityDTO> listCity = cityService.viewAllCity();
            return ResponseEntity.ok().body(new ApiResponse<>(200, listCity, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
