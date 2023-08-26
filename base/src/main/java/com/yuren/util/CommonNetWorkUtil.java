package com.yuren.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


public final class CommonNetWorkUtil {
    private static String MOBILE = "MOBILE";
    private static String WIFI = "WIFI";
    private static String TAG = CommonNetWorkUtil.class.getSimpleName();

    public static final boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        } else {
            return false;
        }
    }

    public static boolean isWifiNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }

        return false;
    }

    public static final void isWifiAvailable(Context context, boolean[] result) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            result[0] = networkInfo.isAvailable();
            result[1] = WIFI.equalsIgnoreCase(networkInfo.getTypeName());
        } else {
            result[0] = false;
            result[1] = false;
        }
    }

    public static boolean is3gNet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }

        return false;
    }

    public static final boolean isCmwap(Context mContext) {
        try {
            ConnectivityManager cm = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info == null) {
                return false;
            }

            String type = info.getTypeName();
            if (MOBILE.equalsIgnoreCase(type)) {
                NetworkInfo mobInfo = cm
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                String extra = mobInfo.getExtraInfo();
                if (extra.startsWith("cmwap")) {
                    return true;
                } else {
                    return false;
                }
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }



    /*public static String encodeParameters(PostParameter[] postParams,
            HttpParams params) {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < postParams.length; j++) {
            try {
                params.setParameter(
                        URLEncoder.encode(postParams[j].name, BaseConfig.ENCODE),
                        URLEncoder.encode(postParams[j].value, BaseConfig.ENCODE));
            } catch (java.io.UnsupportedEncodingException neverHappen) {
                neverHappen.printStackTrace();
            }
          
             if (j != 0) { buf.append("&"); } try {
               buf.append(URLEncoder.encode(postParams[j].name,BuildConfig.
              ENCODE)) .append("=")
             .append(URLEncoder.encode(postParams[j].value,
             BuildConfig.ENCODE)); } catch
             (java.io.UnsupportedEncodingException neverHappen) { }
        }

        return buf.toString();
    }*/


    public static String getHtmlStringContentByGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (!TextUtils.isEmpty(param)) {
                urlNameString = (urlNameString + "?" + param);
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
