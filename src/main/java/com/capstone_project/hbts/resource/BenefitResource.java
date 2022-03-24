package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Benefit.BenefitResult;
import com.capstone_project.hbts.dto.Benefit.ObjectBenefit;
import com.capstone_project.hbts.request.BenefitRequest;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.BenefitService;
import com.capstone_project.hbts.service.BenefitTypeService;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping("/api/v1")
public class BenefitResource {

    private final BenefitService benefitService;

    private final DataDecryption dataDecryption;

    private final BenefitTypeService benefitTypeService;

    public BenefitResource(BenefitService benefitService, DataDecryption dataDecryption,
                           BenefitTypeService benefitTypeService) {
        this.benefitService = benefitService;
        this.dataDecryption = dataDecryption;
        this.benefitTypeService = benefitTypeService;
    }

    /**
     * @param hotelId
     * return
     */
    @GetMapping("/public/list-hotel-benefit")
    public ResponseEntity<?> getListBenefitByHotelId(@RequestParam String hotelId) {
        log.info("REST request to get list benefit by hotel id");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(hotelId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<ObjectBenefit> benefitObjectList = benefitService.getListBenefitByHotelId(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, benefitObjectList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param benefitTypeId
     * return
     */
    @GetMapping("/list-benefit")
    public ResponseEntity<?> getListBenefitByType(@RequestParam String benefitTypeId) {
        log.info("REST request to get list benefit by type");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(benefitTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            List<BenefitResult> benefitList = benefitTypeService.getAllBenefitByTypeId(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, benefitList,
                            null, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param benefitTypeId
     * @param benefitList
     * return
     * @apiNote add list benefit for admin
     */
    @PostMapping("/add-benefit")
    public ResponseEntity<?> addListBenefit(@RequestParam String benefitTypeId,
                                            @RequestBody List<BenefitRequest> benefitList) {
        log.info("REST request to add list benefit");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(benefitTypeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try {
            benefitService.addBenefit(id, benefitList);
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

    /**
     * @param benefitName
     * return
     * @apiNote add benefit for provider that doesn't have in db
     */
    @PostMapping("/add-other-benefit")
    public ResponseEntity<?> addBenefitOtherType(@RequestBody TextNode benefitName) {
        log.info("REST request to add a benefit for provider in other type");

        try {
            benefitService.addBenefitOtherType(benefitName.asText());
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
