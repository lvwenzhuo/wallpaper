package com.yuren.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

public class SharePreferenceUtil {


    /**
     * 保存在手机里面的文件名
     */
    private static String SharePreferncesName = null;

    public static boolean getBoolean(Context context, String fileName, String key, boolean defaultValue) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String fileName, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    private static void ensureSpfname(Context context) {
        if (StringUtil.isEmpty(SharePreferncesName)) {
            SharePreferncesName = context.getPackageName();
        }
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key     键值对的key
     * @param value   键值对的值
     * @return 是否保存成功
     */
    public static boolean setValue(Context context, String key, Object value) {

        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(SharePreferncesName,
                Context.MODE_PRIVATE);


        SharedPreferences.Editor edit = sp.edit();
        if (value instanceof String) {
            return edit.putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            return edit.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Float) {
            return edit.putFloat(key, (Float) value).commit();
        } else if (value instanceof Integer) {
            return edit.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            return edit.putLong(key, (Long) value).commit();
        } else if (value instanceof Set) {
            new IllegalArgumentException("Value can not be Set object!");
            return false;
        }
        return false;
    }

    /**
     * 得到Boolean类型的值
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defaultValue) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(SharePreferncesName,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 得到String类型的值
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Context context, String key,
                                   String defaultValue) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(SharePreferncesName,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 得到Float类型的值
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Float getFloat(Context context, String key, float defaultValue) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(SharePreferncesName,
                Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    /**
     * 得到Int类型的值
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(SharePreferncesName,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 得到Long类型的值
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLong(Context context, String key, long defaultValue) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(SharePreferncesName,
                Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static boolean remove(Context context, String key) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(
                SharePreferncesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清除所有数据
     *
     * @param context
     * @return 是否成功
     */
    public static boolean clear(Context context) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(
                SharePreferncesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        return editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return 是否存在
     */
    public static boolean contains(Context context, String key) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(
                SharePreferncesName, Context.MODE_PRIVATE);
        boolean result = sp.contains(key);

        return result;
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return Map<String, ?>
     */
    public static Map<String, ?> getAll(Context context) {
        ensureSpfname(context);
        SharedPreferences sp = context.getSharedPreferences(
                SharePreferncesName, Context.MODE_PRIVATE);
        return sp.getAll();
    }

}
