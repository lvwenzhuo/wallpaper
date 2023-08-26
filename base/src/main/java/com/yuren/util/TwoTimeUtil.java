package com.yuren.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chen on 17-11-22.
 */

public class TwoTimeUtil {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat ymdhf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private final static SimpleDateFormat md = new SimpleDateFormat("MM-dd");
    private final static SimpleDateFormat ms = new SimpleDateFormat("HH:mm");


    public static String getNowTime() {
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * 时分
     */
    public static String getNowTimeHM() {
        Date date = new Date();
        return ms.format(date);
    }

    public static long getTwoCompute(String date) {

        if (date == null) {
            return -1;
        }

        return getTwoCompute(date, getNowTime());
    }

    public static String ConversionTime(long time) {
        return sdf.format(time);
    }

    public static long getTwoCompute(String date, String date2) {

        if (date == null || date2 == null) {
            return -1;
        }
        try {
            Date d1 = sdf.parse(date);
            Date d2 = sdf.parse(date2);

            return (Math.abs(d2.getTime() - d1.getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //获取时间格式为年月日分
    public static String getTimeYMDM(long time) {
        if (time <= 0) {
            return "";
        }
        try {
            Date date = new Date(time);
            return ymdhf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //获取时间格式为月日
    public static String getTimeMD(long time) {
        if (time <= 0) {
            return "";
        }
        try {
            Date date = new Date(time);
            return md.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    //获得时间格式为年月日
    public static String getTimeYMD(String time) {

        if (time == null) {
            return null;
        }
        try {
            Date date = sdff.parse(time);
            return sdff.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    //获得时间格式为年月日
    public static String getTimeStringToString(String time) {

        if (StringUtil.isEmpty(time)) {
            return null;
        }
        try {
            return sdf.format(time);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }

    }


    //获得时间格式为年月日时
    public static String getTimeYMDS(long time) {

        if (time <= 0) {
            return "";
        }
        try {
            Date date = new Date(time);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static boolean isSimpleDay(String date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        String time = df.format(date);
        String nowTime = df.format(System.currentTimeMillis());
        if (nowTime.equals(time)) {
            return true;
        }

        return false;
    }


    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = System.currentTimeMillis() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }


    public static String getTimeFormatTextString(String date) {
        if (date == null || StringUtil.isEmpty(date)) {
            return null;
        }

        long diff = 0;
        try {
            diff = System.currentTimeMillis() - sdf.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }


    public static String getTimeLongFormatTextString(long date) {
        if (date <= 0) {
            return null;
        }

        long diff = 0;

        diff = System.currentTimeMillis() - date;

        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }
}
