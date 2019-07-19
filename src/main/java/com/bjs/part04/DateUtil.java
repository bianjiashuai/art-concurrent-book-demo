package com.bjs.part04;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author BianJiashuai
 */
public class DateUtil {

    public static String getFormatDate() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());
    }
}
