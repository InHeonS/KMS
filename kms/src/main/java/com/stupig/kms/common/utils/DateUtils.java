package com.stupig.kms.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

    /**
     * Get Now Date
     *
     * <pre>
     *     String nowDate = DateUtils.getNowDate("yyyyMMddHHmmss"); // "20231204163827"
     *     String nowDate = DateUtils.getNowDate("yyyyMMdd");       // "20231204"
     *     String nowDate = DateUtils.getNowDate("yyyy-MM-dd");     // "2023-12-04"
     * </pre>
     *
     * @param format 포맷 ( java.text.SimpleDateFormat 참조 )
     * @return String
     */
    public static String getNowDate(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance(Locale.KOREA).getTime());
    }
}
