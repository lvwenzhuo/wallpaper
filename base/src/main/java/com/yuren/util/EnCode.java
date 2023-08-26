package com.yuren.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 创建日期: 2018/9/13 on 2018/9/13
 * Created by 陈 chenjuan
 * 描述：
 */

public class EnCode {
    public static String encode(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {

            return "";
        }
    }
}
