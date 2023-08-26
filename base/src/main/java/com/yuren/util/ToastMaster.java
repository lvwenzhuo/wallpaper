package com.yuren.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.lang.ref.SoftReference;

public final class ToastMaster {

    // the last time for show
    private static long lastTime;
    // Maybe , the softReference is not meanigful
    private static SoftReference<String> softRef_LastMessage;
    /**
     * 系统toast
     */
    private static Toast mToast;

    // we only need no any instance
    private ToastMaster() {
    }

    /**
     * Show some message for user.<br>
     * <p>
     * we guarantee that It is shown one time in its duration .<br>
     * <p>
     * If the message by msgResIddo not exist , we do not show anything .<br>
     *
     * @param context
     * @param msgResId
     * @param durationType How long to display the message.Either LENGTH_SHORT
     *                     orLENGTH_LONG
     */
    public final static void makeText(Context context, int msgResId, final int durationType) {
        try {
            makeText(context, context.getApplicationContext().getResources().getString(msgResId), durationType);
        } catch (Exception e) {
        }
    }

    /**
     * Show some message for user.<br>
     * <p>
     * we guarantee that It is shown one time in its duration .<br>
     * <p>
     * If the message is empty ( null or empty string) , we do not show anything
     * .<br>
     *
     * @param context
     * @param message
     * @param durationType How long to display the message.Either LENGTH_SHORT
     *                     orLENGTH_LONG
     */
    public final static void makeText(Context context, String message, final int durationType) {
        if (TextUtils.isEmpty(message) || context == null) {
            return;
        }

        // make the duration
        long duration;
        if (durationType == Toast.LENGTH_LONG) {
            duration = 3500;// this is default long time in framework
        } else {
            duration = 2000;// this is default shorttime in framework
        }

        // whether or not show
        if (isLastMessage(message)) {
            long now = System.currentTimeMillis();
            long fff = (now - lastTime);
            if (fff > duration) {
                Toast.makeText(context, message, durationType).show();
                lastTime = now;
            }/*
              * else{ }
              */
        } else {
            Toast.makeText(context, message, durationType).show();
            lastTime = System.currentTimeMillis();
        }
    }

    /**
     * Whether or not current message is the same with the last message .<br>
     * <p>
     * If not , we will set the current message as new message , and put into
     * SoftReference
     *
     * @param lastMessage It must be not null . we have fliter it .
     * @return
     */
    private final static boolean isLastMessage(String lastMessage) {
        boolean isSame = false;
        if (softRef_LastMessage == null || softRef_LastMessage.get() == null) {
            // do nothing. the result has been "false"
        } else {
            isSame = lastMessage.equalsIgnoreCase(softRef_LastMessage.get());
        }

        if (!isSame) {
            softRef_LastMessage = new SoftReference<String>(lastMessage);
        }

        return isSame;
    }

    public static void toast(Context context, String s) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
