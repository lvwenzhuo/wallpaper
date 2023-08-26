package com.yuren.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public final class MD5Util {
    public static String convertToMD5(String source) {
        // first , check
        if (TextUtils.isDigitsOnly(source)) {
            return null;
        }
        try {
            return getMessageDigest(source.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return source;
        } catch (Throwable e) {
            e.printStackTrace();
            return source;
        }
    }

    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
