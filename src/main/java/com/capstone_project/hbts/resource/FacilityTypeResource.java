package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.dto.facility.FacilityTypeDTO;
import com.capstone_project.hbts.request.FacilityTypeRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.FacilityTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1")
public class FacilityTypeResource {

    private final FacilityTypeService facilityTypeService;

    public FacilityTypeResource(FacilityTypeService facilityTypeService) {
        this.facilityTypeService = facilityTypeService;
    }

    @GetMapping("/get-facility-type")
    public ResponseEntity<?> getAllFacilityType() {
        log.info("REST request to get all facility type");
        try {
            List<FacilityTypeDTO> facilityTypeDTOList = facilityTypeService.getAllFacilityType();
            return ResponseEntity.ok().body(new ApiResponse<>(200, facilityTypeDTOList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    @PostMapping("/add-facility-type")
    public ResponseEntity<?> addFacilityType(@RequestBody FacilityTypeRequest facilityTypeRequest) {
        log.info("REST request to add facility type");
        try {
            facilityTypeService.addFacilityType(facilityTypeRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}