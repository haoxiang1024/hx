package com.school.utils;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/*时间工具类
 */
public class DateUtil {
    public static final String STANDARD = "yyyy-MM-dd HH:mm:ss";

    //将String转成Date
    public static Date string2Date(String strDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD);
        DateTime dateTime = dateTimeFormatter.parseDateTime(strDate);
        return dateTime.toDate();
    }

    //将Date转成String
    public static String date2String(Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD);
    }

    //获取当前时间
    public static Date getTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(STANDARD);
        String format = formatter.format(date);
        return string2Date(format);
    }
}
