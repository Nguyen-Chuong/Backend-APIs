package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.dto.Benefit.BenefitTypeDTO;
import com.capstone_project.hbts.request.BenefitTypeRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.BenefitTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1")
public class BenefitTypeResource {

    private final BenefitTypeService benefitTypeService;

    public BenefitTypeResource(BenefitTypeService benefitTypeService) {
        this.benefitTypeService = benefitTypeService;
    }

    /**
     * @param
     */
    @GetMapping("/get-benefit-type")
    public ResponseEntity<?> getAllBenefitType() {
        log.info("REST request to get all benefit type");
        try {
            List<BenefitTypeDTO> benefitTypeDTOList = benefitTypeService.getAllBenefitType();
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, benefitTypeDTOList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param benefitTypeRequest
     */
    @PostMapping("/add-benefit-type")
    public ResponseEntity<?> addBenefitType(@RequestBody BenefitTypeRequest benefitTypeRequest) {
        log.info("REST request to add benefit type");
        try {
            benefitTypeService.addBenefitType(benefitTypeRequest);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, null,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
