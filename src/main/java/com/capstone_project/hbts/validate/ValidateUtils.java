package com.capstone_project.hbts.validate;

import java.util.Date;

public class ValidateUtils {

    // validate from date before to date
    public static boolean isFromDateBeforeToDate(Date fromDate, Date toDate) {
        try {
            return fromDate.before(toDate);
        } catch (Exception e) {
            return false;
        }
    }

}
