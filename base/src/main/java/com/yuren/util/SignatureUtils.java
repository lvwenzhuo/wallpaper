package com.yuren.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeMap;


public class SignatureUtils {
    public static String generateSignature(TreeMap params, String secret) throws Exception {
        StringBuilder paramsBase = new StringBuilder("");
        Set<String> sortKeys = params.keySet();
        for (String key : sortKeys) {
            paramsBase.append(key).append("=").append(params.get(key)).append("&");
        }
        String enParams = paramsBase.toString();
        String baseStr = enParams + secret;
        return Coder.encryptMD5(baseStr);
    }

    /**
     * 将参数URLEncode为UTF-8
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlEncodeUTF8(String params) throws UnsupportedEncodingException {
        String en = URLEncoder.encode(params, "UTF8");
        en = en.replace("+", "%20");
        en = en.replace("*", "%2A");
        return en;
    }
}
