package com.capstone_project.hbts.helper;

import com.capstone_project.hbts.entity.City;
import com.capstone_project.hbts.entity.District;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // check if data import has excel format or not
    public boolean hasExcelFormat(MultipartFile multipartFile) {
        return TYPE.equals(multipartFile.getContentType());
    }

    // read excel file and convert it into object
    public List<District> convertExcelToObjectDistrict(InputStream inputStream, int cityId) {
        try {
            // create new workbook base on excel file input
            Workbook workbook = new XSSFWorkbook(inputStream);
            // read the first sheet
            Sheet sheet = workbook.getSheetAt(0);
            // loop through the rows
            Iterator<Row> rowIterator = sheet.rowIterator();
            // new list data
            List<District> districtList = new ArrayList<>();
            int rowNumber = 0;
            // number of non empty rows
            int notNullRowCount = 0;
            // count number of non empty rows
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if (cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        if (cell.getCellType() != Cell.CELL_TYPE_STRING ||
                                cell.getStringCellValue().length() > 0) {
                            notNullRowCount++;
                            // recognize empty row, break the loop
                            break;
                        }
                    }
                }
            }
            // loop through row
            while (rowIterator.hasNext()) {
                rowNumber += 1;
                // loop till the last non empty row, break
                if (rowNumber == (notNullRowCount + 1)) {
                    break;
                }
                Row currentRow = rowIterator.next();
                if (currentRow.getRowNum() == 0) {
                    continue;
                }
                // loop through the cell
                District district = new District();
                // set name district, city
                district.setNameDistrict(currentRow.getCell(0).getStringCellValue());
                City city = new City();
                city.setId(cityId);
                district.setCity(city);
                districtList.add(district);
            }
            workbook.close();
            return districtList;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
