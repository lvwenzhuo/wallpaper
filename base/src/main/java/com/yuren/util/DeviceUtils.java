package com.yuren.util;

import static com.yongzemei.util.StringUtil.isEmpty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
import java.util.UUID;


public class DeviceUtils {

    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getDeviceId();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getPhoneNumber(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getLine1Number();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getNetworkCountryIso(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getNetworkCountryIso();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getNetworkOperator(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getNetworkOperator();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getNetworkOperatorName();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @return
     * @see #NETWORK_TYPE_UNKNOWN
     * @see #NETWORK_TYPE_GPRS
     * @see #NETWORK_TYPE_EDGE
     * @see #NETWORK_TYPE_UMTS
     * @see #NETWORK_TYPE_HSDPA
     * @see #NETWORK_TYPE_HSUPA
     * @see #NETWORK_TYPE_HSPA
     * @see #NETWORK_TYPE_CDMA
     * @see #NETWORK_TYPE_EVDO_0
     * @see #NETWORK_TYPE_EVDO_A
     * @see #NETWORK_TYPE_EVDO_B
     * @see #NETWORK_TYPE_1xRTT
     * @see #NETWORK_TYPE_IDEN
     * @see #NETWORK_TYPE_LTE
     * @see #NETWORK_TYPE_EHRPD
     * @see #NETWORK_TYPE_HSPAP
     */
    public static int getNetworkType(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getNetworkType();
        } catch (Exception e) {
            return TelephonyManager.NETWORK_TYPE_UNKNOWN;
        }
    }

    /**
     * @return
     * @see #PHONE_TYPE_NONE
     * @see #PHONE_TYPE_GSM
     * @see #PHONE_TYPE_CDMA
     * @see #PHONE_TYPE_SIP
     */
    public static int getPhoneType(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getPhoneType();
        } catch (Exception e) {
            return TelephonyManager.PHONE_TYPE_NONE;
        }
    }

    public static String getSimCountryIso(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getSimCountryIso();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSimOperator(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getSimOperator();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSimOperatorName(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getSimOperatorName();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getSimSerialNumber(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getSimSerialNumber();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    /*
     * @return
     * @see #SIM_STATE_UNKNOWN
     * @see #SIM_STATE_ABSENT
     * @see #SIM_STATE_PIN_REQUIRED
     * @see #SIM_STATE_PUK_REQUIRED
     * @see #SIM_STATE_NETWORK_LOCKED
     * @see #SIM_STATE_READY
     */
    public static int getSimState(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSimState();
        } catch (Exception e) {
            return TelephonyManager.SIM_STATE_UNKNOWN;
        }
    }

    /**
     * IMSI
     *
     * @return
     */
    public static String getSubscriberId(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getSubscriberId();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getVoiceMailNumber(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.
                    getSystemService(Context.TELEPHONY_SERVICE);
            String v = tm.getVoiceMailNumber();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取mac地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.
                    getSystemService(Context.WIFI_SERVICE);
            String v = wifi.getConnectionInfo().getMacAddress();
            if (v == null) {
                v = "";
            }
            return v;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取设备唯一编码 md5(imei+mac)
     *
     * @return
     */
    public static String getUniqueId(Context context) {

        if(UNIQUE_ID!=null){
            return UNIQUE_ID;
        }

        try {
            String imei = getIMEI(context);
            String macAddr = getMacAddress(context);
            String unique = imei + macAddr;
            return UNIQUE_ID = CommonUtil.MD5(unique);
        } catch (Exception e) {
            UNIQUE_ID = getUUID(context);
            return UNIQUE_ID;
        }
    }


    private static String UNIQUE_ID = null;

    /**
     * 获取手机序列号
     *
     * @return 手机序列号
     */
    @SuppressLint({"NewApi", "MissingPermission"})
    public static String getSerialNumber() {
        String serial = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//9.0+
                serial = Build.getSerial();
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+
                serial = Build.SERIAL;
            } else {//8.0-
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  Log.e("e", "读取设备序列号异常：" + e.toString());
        }
        return serial;
    }



    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        SharedPreferences mShare = context.getSharedPreferences("sysCacheMap", Context.MODE_WORLD_WRITEABLE);

        String uuid = mShare.getString("uuid", null);
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString("uuid", uuid).apply();
        }

        return uuid;
    }
}
