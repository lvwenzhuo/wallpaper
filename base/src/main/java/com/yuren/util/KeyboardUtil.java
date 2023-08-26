package com.yuren.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {


    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void hideSoftInput(Context context) {
        try {
            if (context instanceof Activity) {
                hideSoftInput(context, (Activity) context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param activity
     */
    public static void hideSoftInput(Context context, Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideSoftInput(Context context, View view) {
        if (view == null || context == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*public static void sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
}
