package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.request.BenefitAddRequest;
import com.capstone_project.hbts.request.BenefitRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.BenefitService;
import com.capstone_project.hbts.service.BenefitTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1")
public class BenefitResource {

    private final BenefitService benefitService;

    private final DataDecryption dataDecryption;

    private final BenefitTypeService benefitTypeService;

    public BenefitResource(BenefitService benefitService, DataDecryption dataDecryption, BenefitTypeService benefitTypeService) {
        this.benefitService = benefitService;
        this.dataDecryption = dataDecryption;
        this.benefitTypeService = benefitTypeService;
    }

    /**
     * return
     */
    @GetMapping("/public/list-hotel-benefit")
    public ResponseEntity<?> getListBenefitByHotelId(@RequestParam String hotelId) {
        log.info("REST request to get list benefit by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<ObjectBenefit> benefitObjectList = benefitService.getListBenefitByHotelId(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, benefitObjectList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    @GetMapping("/list-benefit")
    public ResponseEntity<?> getListBenefitByType(@RequestParam String benefitTypeId) {
        log.info("REST request to get list benefit by type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(benefitTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<BenefitResult> benefitList = benefitTypeService.getAllBenefitByTypeId(id);
            return ResponseEntity.ok().body(new ApiResponse<>(200, benefitList, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote add list benefit for admin
     */
    @PostMapping("/add-benefit")
    public ResponseEntity<?> addListBenefit(@RequestParam String benefitTypeId, @RequestBody BenefitAddRequest benefitAddRequest) {
        log.info("REST request to add list benefit");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(benefitTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            benefitService.addBenefit(id, benefitAddRequest);
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @apiNote add benefit for provider that doesn't have in db
     */
    @PostMapping("/add-other-benefit")
    public ResponseEntity<?> addBenefitOtherType(@RequestBody BenefitRequest benefitRequest) {
        log.info("REST request to add a benefit for provider in other type");
        try {
            benefitService.addBenefitOtherType(benefitRequest.getName());
            return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
        }
    }

}
