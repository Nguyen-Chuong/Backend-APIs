package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constants.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.dto.Location.DistrictSearchDTO;
import com.capstone_project.hbts.dto.Location.ResultSearch;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.DistrictService;
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
public class DistrictResource {

    private final DistrictService districtService;

    private final DataDecryption dataDecryption;

    public DistrictResource(DistrictService districtService, DataDecryption dataDecryption) {
        this.districtService = districtService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @param text
     * return
     */
    @GetMapping("/public/search-district")
    public ResponseEntity<?> searchDistrict(@RequestParam String text){
        log.info("REST request to search district by text");

        try{
            List<DistrictSearchDTO> districtSearchDTOList = districtService.searchDistrict(text);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, districtSearchDTOList,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param text
     * return
     */
    @GetMapping("/public/search-city-district")
    public ResponseEntity<?> searchCityDistrict(@RequestParam String text){
        log.info("REST request to search city and district by text");

        try{
            List<ResultSearch> cityDistricts = districtService.searchDistrictCity(text);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, cityDistricts,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

    /**
     * @param cityId
     * return
     */
    @GetMapping("/get-district")
    public ResponseEntity<?> getDistrictInCity(@RequestParam String cityId){
        log.info("REST request to get all district in city");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(cityId);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_DATA_001, ErrorConstant.ERR_DATA_001_LABEL));
        }
        try{
            List<DistrictSearchDTO> districtSearchDTOList = districtService.getAllDistrictInCity(id);
            return ResponseEntity.ok()
                    .body(new ApiResponse<>(200, districtSearchDTOList,
                            null, null));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null,
                            ErrorConstant.ERR_000, ErrorConstant.ERR_000_LABEL));
        }
    }

}
