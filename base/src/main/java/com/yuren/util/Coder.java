package com.yuren.util;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 各种加密算法
 * Date: 14-7-13
 * Time: 上午11:24
 */
public class Coder {
    public static final String KEY_SHA = "SHA";
    public static final String KEY_MD5 = "MD5";

    /**
     * MAC算法可选以下多种算法
     * <p/>
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacSHA1";
    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final String   ENCODE ="UTF-8";
    /**
     * 将参数URLEncode为UTF-8
     */
    public static String encodeUTF8(String params) {
        try {
            String en = URLEncoder.encode(params,  ENCODE);

           // String en = URLEncoder.encode(params, BaseConfig.ENCODE);
            en = en.replace("+", "%20");
            en = en.replace("*", "%2A");
            return en;
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    /**
     * 将参数URLEncode，默认为UTF-8
     */
    public static String encode(String params, String charset) {
        try {
            String en = URLEncoder.encode(params, charset != null ? charset :  ENCODE);
            en = en.replace("+", "%20");
            en = en.replace("*", "%2A");
            return en;
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    /**
     * 将参数URLDecoder为UTF-8
     */
    public static String decodeUTF8(String params) {
        try {
            String de = URLDecoder.decode(params,  ENCODE);
            return de;
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data.getBytes());

        return toHexString(md5.digest());

    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5_Byte(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
      //  md5.update(data.getBytes(BaseConfig.ENCODE));
        md5.update(data.getBytes("UTF-8"));

        return md5.digest();
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptMD5GBK(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data.getBytes("GBK"));
        return toHexString(md5.digest());

    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(String data, byte[] key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(key, KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data.getBytes("UTF-8"));

      //  return mac.doFinal(data.getBytes(BaseConfig.ENCODE));

    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte by : b) {
            sb.append(HEXCHAR[(by & 0xf0) >>> 4]);
            sb.append(HEXCHAR[by & 0x0f]);
        }
        return sb.toString();
    }

    public static byte[] toBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
