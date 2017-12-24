package com.china.kuaiyou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static String getNowTimeString() {

        Date nowDate = new Date();
        return sdf.format(nowDate);
    }

    public static long getNowTimeLong() {
        return new Date().getTime();
    }

    public static long StringTimeToLong(String time) throws ParseException {
        return sdf.parse(time).getTime();
    }

    public static String getDate() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        Date curDate = new Date(System.currentTimeMillis());
        cal.setTime(curDate);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
//			System.out.println("--------------" + weekDays[w]);
        return weekDays[w];

    }

    public static void main(String[] args) {

        System.out.println(TimeUtil.getTimestamp());
    }

    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd?hh:mm:ss");
        Date nowDate = new Date();
        String time = sdf.format(nowDate);
        int index = time.indexOf("?");
        String time1 = time.substring(0, index);
        String time2 = time.substring(index + 1, time.length());
        StringBuilder sb = new StringBuilder();
        sb.append(time1);
        sb.append("T");
        sb.append(time2);
        sb.append("Z");
        return sb.toString();
    }

}
