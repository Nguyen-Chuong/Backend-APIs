package com.capstone_project.hbts.service;

import com.capstone_project.hbts.entity.District;
import com.capstone_project.hbts.helper.ExcelHelper;
import com.capstone_project.hbts.repository.DistrictRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelServiceImpl{

    private final ExcelHelper excelHelper;

    private final DistrictRepository districtRepository;

    public ExcelServiceImpl(ExcelHelper excelHelper, DistrictRepository districtRepository) {
        this.excelHelper = excelHelper;
        this.districtRepository = districtRepository;
    }

    public void saveExcelDataToTableDatabase(MultipartFile multipartFile, int cityId) {
        try {
            List<District> districtList = excelHelper.convertExcelToObjectDistrict(multipartFile.getInputStream(), cityId);
            // batch processing w/ max num = 10
            districtRepository.saveAll(districtList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasExcelFormat(MultipartFile multipartFile) {
        return excelHelper.hasExcelFormat(multipartFile);
    }

}
