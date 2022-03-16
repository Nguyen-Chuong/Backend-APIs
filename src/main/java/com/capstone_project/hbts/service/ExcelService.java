package com.capstone_project.hbts.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    /**
     * save data from excel file to table database
     * @param multipartFile
     * @param cityId
     */
    void saveExcelDataToTableDatabase(MultipartFile multipartFile, int cityId);

    /**
     * to check if data import has excel format or not
     * @param multipartFile
     */
    boolean hasExcelFormat(MultipartFile multipartFile);

}
