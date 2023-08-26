package com.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


import com.yuren.util.NetWorkType;

import java.util.Map;


public class NetWorkUtil {


    /**
     * 需要的话，在头部插入 宽高
     *
     * @param url
     * @param isRelatedDevice
     * @return
     */
    private static StringBuilder initUrlWithArgs(String url, boolean isRelatedDevice) {
        StringBuilder urlBuilder = new StringBuilder(url);
        if (url.contains("?")) {
            if (!url.endsWith("?")) {
                urlBuilder.append("&");
            }
        } else {
            urlBuilder.append("?");
        }

      /*  if (isRelatedDevice) {
            int[] wh = AppConfig.getScreenWh();
            urlBuilder.append("s_width=").append(wh[0]).append("&");
            urlBuilder.append("s_height=").append(wh[1]);
        }*/

        return urlBuilder;
    }

   /* private static void initWithDeviceArgs(Map<String, String> args) {
        int[] wh = AppConfig.getScreenWh();
        args.put("s_width=", String.valueOf(wh[0]));
        args.put("s_height=", String.valueOf(wh[1]));
    }*/


    public static boolean isMobileEnable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = (cm == null) ? null : cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isWifiEnable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = (cm == null) ? null : cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }


    public static boolean isNetworkEnable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static NetWorkType getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return NetWorkType.TYPE_UNKNOWN;
        }


        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetWorkType.TYPW_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String subTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by 11
                        return NetWorkType.TYPE_2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  // api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  // api<13 : replace by 15
                        return NetWorkType.TYPE_3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:    // api<11 : replace by 13
                        return NetWorkType.TYPE_4G;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (subTypeName.equalsIgnoreCase("TD-SCDMA") || subTypeName.equalsIgnoreCase("WCDMA")
                                || subTypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetWorkType.TYPE_3G;
                        } else {
                            return NetWorkType.TYPE_UNKNOWN;
                        }
                }

            }
        }

        return NetWorkType.TYPE_UNKNOWN;
    }

    @SuppressLint("MissingPermission")
    public static boolean isFastMobileNetwork(Context context) {
        if (!isMobileEnable(context)) {
            return false;
        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return false;
        }


        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

}
