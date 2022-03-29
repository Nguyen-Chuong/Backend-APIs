package com.capstone_project.hbts.resource;

import com.capstone_project.hbts.constant.ErrorConstant;
import com.capstone_project.hbts.decryption.DataDecryption;
import com.capstone_project.hbts.response.ApiResponse;
import com.capstone_project.hbts.service.ExcelServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Log4j2
@RequestMapping("/api/v1")
public class ExcelResource {

    private final ExcelServiceImpl excelService;

    private final DataDecryption dataDecryption;

    public ExcelResource(ExcelServiceImpl excelService, DataDecryption dataDecryption) {
        this.excelService = excelService;
        this.dataDecryption = dataDecryption;
    }

    /**
     * @apiNote for admin can use excel file to add list district to database
     */
    @PostMapping(value = "/upload-file-excel-district", consumes = {"application/json", MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFileExcel(@RequestPart(value = "file") MultipartFile multipartFile, @RequestParam String cityId) {
        log.info("REST request to import list district into table database");
        int id;
        try {
            id = dataDecryption.convertEncryptedDataToInt(cityId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_001_LABEL));
        }
        if (excelService.hasExcelFormat(multipartFile)) {
            try {
                excelService.saveExcelDataToTableDatabase(multipartFile, id);
                return ResponseEntity.ok().body(new ApiResponse<>(200, null, null));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_000_LABEL));
            }
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, null, ErrorConstant.ERR_DATA_002_LABEL));
        }
    }

}