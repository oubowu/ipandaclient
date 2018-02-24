package com.oubowu.ipanda.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Oubowu on 2018/2/24 10:10.
 */
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String format(String dateLine) {
        return sdf.format(new Date(Long.parseLong(dateLine)*1000));
    }

}
