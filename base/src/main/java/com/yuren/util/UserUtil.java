package com.yuren.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UserUtil {

    private static final String SPF_NAME = "_user_info";
    private static final String TOKEN_NAME = "_token";
    private static final String USER_ID = "_user_id";
    private static final String LOGIN_TIME = "_login_time";
    private static final String DATA_JSON = "_data_json";

    public static void saveNewToken(final Context context, String token, String userId) {
        if (context == null) {
            return;
        }

        SharedPreferences.Editor spEditor = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE).edit();
        spEditor.putString(TOKEN_NAME, token);
        spEditor.putString(USER_ID, userId);

        spEditor.commit();
    }

    public static void saveUserInfo(final Context context, String info) {
        if (context == null) {
            return;
        }

        SharedPreferences.Editor spEditor = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE).edit();
        spEditor.putString(DATA_JSON, info);

        spEditor.commit();
    }

    public static void clearUserInfo(final Context context ) {
        if (context == null) {
            return;
        }

        SharedPreferences.Editor spEditor = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE).edit();
        spEditor.putString(DATA_JSON,"");

        spEditor.commit();
    }

    public static String readUserInfo(final Context context) {
        if (context == null) {
            return null;
        }

        return context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE).getString(DATA_JSON, null);
    }

    public static void clearToken(final Context context) {
        if (context == null) {
            return;
        }

        SharedPreferences.Editor spEditor = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE).edit();
        spEditor.remove(TOKEN_NAME);
        spEditor.remove(USER_ID);

        spEditor.commit();
    }

    public static String findToken(final Context context) {
        if (context == null) {
            return null;
        }

        SharedPreferences spEditor = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spEditor.getString(TOKEN_NAME, null);
    }

    public static String findUserId(final Context context) {
        if (context == null) {
            return null;
        }

        SharedPreferences spEditor = context.getSharedPreferences(SPF_NAME, Context.MODE_PRIVATE);
        return spEditor.getString(USER_ID, null);
    }
}
