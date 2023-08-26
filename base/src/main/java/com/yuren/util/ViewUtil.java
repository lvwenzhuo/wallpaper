package com.yuren.util;

import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;


public class ViewUtil {
    private static AtomicInteger sNextGeneratedId; // 版本小于17才会初始化

    private ViewUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void setViewId(final View view) {
        if (Build.VERSION.SDK_INT >= 17) { // 17是4.2
            view.setId(View.generateViewId());
        } else {
            view.setId(generateViewId());
        }

    }

    private static int generateViewId() {
        if (sNextGeneratedId == null) {  // 版本小于17才会初始化
            sNextGeneratedId = new AtomicInteger(1);
        }

        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) {
                newValue = 1; // Roll over to 1, not 0.
            }
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
