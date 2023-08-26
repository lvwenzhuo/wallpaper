package com.yuren.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final BigDecimal BIG_DECIMAL_100_FEN = new BigDecimal(100);

    public static boolean isEmpty(String source) {
        return source == null || source.trim().length() <= 0;
    }

    public static boolean isEmpty(CharSequence source) {
        return source == null || source.length() == 0;
    }


    public static String formatMoney(long number) {
        // 先改掉下数字
        if (number < 10) {
            return new String("0.0" + number);
        } else if (number <= 99) {
            return new String("0." + number);
        }

        // 将传进数字反转。 现在至少等于100
        StringBuilder reverseSb = new StringBuilder().append(number).reverse();
        String reverseStr = reverseSb.toString();
        String strTemp = null;
       /* if(reverseStr.startsWith("00")){
            strTemp="";
        }else{ */
        strTemp = (reverseStr.substring(0, 2) + ".");
        // }
        reverseStr = reverseSb.delete(0, 2).toString();


        for (int i = 0; i < reverseStr.length(); i++) { // i从2开始，是因为开头的是 分 为单位
            if (i * 3 + 3 > reverseStr.length()) {
                strTemp += reverseStr.substring(i * 3, reverseStr.length());
                break;
            }
            strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";
        }
        // 将[789,456,] 中最后一个[,]去除
        if (strTemp.endsWith(",")) {
            strTemp = strTemp.substring(0, strTemp.length() - 1);
        }
        // 将数字重新反转
        String resultStr = new StringBuilder(strTemp).reverse().toString();
        return resultStr;
    }


    //金额 元 转 分
    public static long priceYuanToFen(String price) {
        if (StringUtil.isEmpty(price)) {
            return 0;
        }
        BigDecimal b = new BigDecimal(price).multiply(BIG_DECIMAL_100_FEN);
        return b.longValue();
    }

    public static long priceYuanToFen(long price) {
        if (price <= 0) {
            return 0;
        }
        BigDecimal b = new BigDecimal(price).multiply(BIG_DECIMAL_100_FEN);
        return b.longValue();
    }

    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    //显示 long 金额 分 转 元
    public static String priceChange(long price) {

        if (price <= 0) {
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(price).divide(BIG_DECIMAL_100_FEN);
        bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(bigDecimal);
    }

    //显示 double 金额 分 转 元
    public static String priceFenToYuan(double price) {

        BigDecimal bigDecimal = new BigDecimal(price).divide(BIG_DECIMAL_100_FEN);
        bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(bigDecimal);
    }

    //显示 String 金额 分 转 元
    public static String priceStringChange(String price) {
        if (isEmpty(price)) {
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(price).divide(BIG_DECIMAL_100_FEN);
        bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(bigDecimal);
    }

    //显示 int 金额 分 转 元
    public static String priceIntChange(int price) {

        BigDecimal bigDecimal = new BigDecimal(price).divide(BIG_DECIMAL_100_FEN);
        bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(bigDecimal);
    }

    public static String conversionDistance(float distance) { //距离显示

        if (distance >= 1000) {
            int a = (int) distance / 1000;
            int b = ((int) distance % 1000) / 100;
            return b > 0 ? (a + "." + b + "km") : (a + "km");
        } else {
            return (int) distance + "m";
        }
    }

    /**
     * 去掉手机号内除数字外的所有字符 * * @param phoneNum 手机号 * @return
     */
    public static String formatPhoneNum(String phoneNum) {
        String regex = "(\\+86)|[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.replaceAll("");
    }


    /**
     * 打开地图App 有经纬度
     *
     * @param lat
     * @param lng
     * @param addr
     * @return
     */
    public static String getAddressDetailUrl(String lat, String lng, String addr) {

        String strUrl = "geo:%1$s,%2$s?q=%3$s";

        return String.format(strUrl, lat, lng, addr);

    }

    /**
     * 打开地图App 只有地址信息
     *
     * @param addr
     * @return
     */
    public static String getAddressUrl(String addr) {

        String strUrl = "geo:q=%1$s";

        return String.format(strUrl, addr);

    }


    /**
     * 网页版地图 有经纬度
     *
     * @param lat
     * @param lng
     * @param addr
     * @return
     */
    public static String getHtmlAddressDetailUrl(String lat, String lng, String addr) {

        String strUrl = "http://api.map.baidu.com/marker?location=%1$s,%2$s&title=%3$s&content=%4$s&output=html";

        return String.format(strUrl, lat, lng, addr, addr);

    }

    /**
     * 网页版地图  只有地址
     *
     * @param addr
     * @return
     */
    public static String getHtmlAddressUrl(String addr) {

        String strUrl = "http://api.map.baidu.com/geocoder?address=%1$s&output=html";

        return String.format(strUrl, addr);

    }

    //String 分割 返回集合
    public static List<String> getList(String text, String fenge) {
        if (isEmpty(text)) {
            return null;
        }
        return new ArrayList<>(Arrays.asList(text.split(fenge)));
    }

    public static String getListFirstString(String text, String fenge) {
        if (isEmpty(text)) {
            return null;
        }

        List<String> list = getList(text, fenge);
        if (list == null || list.size() <= 0) {
            return null;
        }

        return list.get(0);
    }

    public static String join(List<String> strings, String delimiter) {
        if (strings == null || strings.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : strings) {
            builder.append(str);
            builder.append(delimiter);
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    /*  获取星星的等级 */
    public static float getRatingBar(float ratingBar) {

        /*  ratingBar 值<= 0 直接返回 */
        if (ratingBar <= 0) {
            return 0;
        }

        /*  */
        int ratingBarInt = (int) ratingBar;
        float xiaoshu = ratingBar - ratingBarInt;

        float five = 0.50f;
        /* >= 0.5  返回数据 +0.5*/
        if (xiaoshu >= five) {
            return ratingBarInt + five;
        }

        //返回原值
        return ratingBarInt;
    }
}
