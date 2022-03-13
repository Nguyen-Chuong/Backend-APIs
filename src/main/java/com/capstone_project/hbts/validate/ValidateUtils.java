package com.capstone_project.hbts.validate;

import com.capstone_project.hbts.constants.ValidateConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateUtils {

    // validate date
    public static boolean validateDate(String date) {
        try {
            new SimpleDateFormat(ValidateConstant.DATE_FORMAT).parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isFromDateBeforeToDate(Date fromDate, Date toDate) {
        try {
            return fromDate.before(toDate);
        } catch (Exception e) {
            return false;
        }
    }

}
