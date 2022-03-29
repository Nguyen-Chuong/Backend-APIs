package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Facility.FacilityResult;
import com.capstone_project.hbts.request.FacilityAddRequest;
import com.capstone_project.hbts.request.FacilityRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.FacilityServiceImpl;
import com.capstone_project.hbts.service.FacilityTypeServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1")
public class FacilityResource {

    private final DataDecryption dataDecryption;

    private final FacilityTypeServiceImpl facilityTypeService;

    private final FacilityServiceImpl facilityService;

    public FacilityResource(DataDecryption dataDecryption, FacilityTypeServiceImpl facilityTypeService, FacilityServiceImpl facilityService) {
        this.dataDecryption = dataDecryption;
        this.facilityTypeService = facilityTypeService;
        this.facilityService = facilityService;
    }

    @GetMapping("/list-facility")
    public ResponseEntity<?> getListFacilityByType(@RequestParam String facilityTypeId) {
        log.info("REST request to get list facility by type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(facilityTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<FacilityResult> facilityList = facilityTypeService.getAllFacilityByTypeId(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, facilityList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote add list facility for admin
     */
    @PostMapping("/add-facility")
    public ResponseEntity<?> addListFacility(@RequestParam String facilityTypeId,
                                             @RequestBody FacilityAddRequest facilityAddRequest) {
        log.info("REST request to add list facility");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(facilityTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            facilityService.addFacility(id, facilityAddRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote add facility for provider that doesn't have in db
     */
    @PostMapping("/add-other-facility")
    public ResponseEntity<?> addFacilityOtherType(@RequestBody FacilityRequest facilityRequest) {
        log.info("REST request to add a facility for provider in other type");
        try {
            facilityService.addFacilityOtherType(facilityRequest.getName());
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
