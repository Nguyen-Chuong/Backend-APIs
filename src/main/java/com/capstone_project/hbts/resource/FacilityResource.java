package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Facility.FacilityResult;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.FacilityTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("/api/v1")
public class FacilityResource {

    private final DataDecryption dataDecryption;

    private final FacilityTypeService facilityTypeService;

    public FacilityResource(DataDecryption dataDecryption, FacilityTypeService facilityTypeService) {
        this.dataDecryption = dataDecryption;
        this.facilityTypeService = facilityTypeService;
    }

    /**
     * @param facilityTypeId
     * return
     */
    @GetMapping("/list-facility")
    public ResponseEntity<?> getListFacilityByType(@RequestParam String facilityTypeId) {
        log.info("REST request to get list facility by type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(facilityTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<FacilityResult> facilityList = facilityTypeService.getAllFacilityByTypeId(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, facilityList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
