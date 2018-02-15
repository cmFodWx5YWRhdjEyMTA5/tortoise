package com.android.nobug.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class StringUtil {

    public static String toNumFormat(int num) {
        DecimalFormat decimal = new DecimalFormat("#,###");
        return decimal.format(num);
    }

    public static String toMoneyFormat(double num) {
        DecimalFormat decimal = new DecimalFormat("#0.00000");
        return decimal.format(num);
    }

    public static String toSentFormat(double value, int unit) {
        String format = "$%." + unit + "f";
        return String.format(format , value);
    }

    public static String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String toDate(long milliSecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(milliSecond));
    }

    public static Calendar getDate(String strDate, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = dateFormat.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String makePhoneNumber(String phoneNumber) {
        String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";

        if(!Pattern.matches(regEx, phoneNumber)) return null;

        return phoneNumber.replaceAll(regEx, "$1-$2-$3");

    }

    //  ========================================================================================

    public static boolean isEnglish(String value) {
        String first = value.substring(0, 1);
        return Pattern.matches("^[a-zA-Z0-9]+$", first);
    }

}
