package com.yuren.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.io.IOException;
import java.io.InputStream;


/**
 * 获取res中的资源
 *
 * @author xuexiang
 * @since 2018/12/18 上午12:14
 */
public final class ResUtils {

    private ResUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取resources对象
     *
     * @return
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(Context context,@StringRes int resId) {
        return getResources(context).getString(resId);
    }



    /**
     * 获取资源图片【和主体有关】
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resId);
        }
        return context.getResources().getDrawable(resId);
    }

    /**
     * 获取dimes值
     *
     * @param resId
     * @return
     */
    public static float getDimens(Context context,@DimenRes int resId) {
        return getResources(context).getDimension(resId);
    }

    /**
     * 获取Color值
     *
     * @param resId
     * @return
     */
    public static int getColor(Context context,@ColorRes int resId) {
        return getResources(context).getColor(resId);
    }

    /**
     * 获取ColorStateList值
     *
     * @param resId
     * @return
     */
    public static ColorStateList getColors(Context context,@ColorRes int resId) {
        return getResources(context).getColorStateList(resId);
    }

    /**
     * 获取dimes值【px不会乘以denstiy.】
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(Context context,@DimenRes int resId) {
        return getResources(context).getDimensionPixelOffset(resId);
    }

    /**
     * 获取dimes值【getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.】
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelSize(Context context,@DimenRes int resId) {
        return getResources(context).getDimensionPixelSize(resId);
    }

    /**
     * 获取字符串的数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(Context context,@ArrayRes int resId) {
        return getResources(context).getStringArray(resId);
    }

    /**
     * 获取数字的数组
     *
     * @param resId
     * @return
     */
    public static int[] getIntArray(Context context,@ArrayRes int resId) {
        return getResources(context).getIntArray(resId);
    }

    /**
     * 获取动画
     *
     * @param resId
     * @return
     */
    public static Animation getAnim(Context context,@AnimRes int resId) {
        return AnimationUtils.loadAnimation(context, resId);
    }

    /**
     * Check if layout direction is RTL
     *
     * @return {@code true} if the layout direction is right-to-left
     */
    public static boolean isRtl(Context context) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                getResources(context).getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    /**
     * Darkens a color by a given factor.
     *
     * @param color
     *     the color to darken
     * @param factor
     *     The factor to darken the color.
     * @return darker version of specified color.
     */
    public static int darker(int color, float factor) {
        return Color.argb(Color.alpha(color), Math.max((int) (Color.red(color) * factor), 0),
                Math.max((int) (Color.green(color) * factor), 0),
                Math.max((int) (Color.blue(color) * factor), 0));
    }

    /**
     * Lightens a color by a given factor.
     *
     * @param color
     *     The color to lighten
     * @param factor
     *     The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *     color white.
     * @return lighter version of the specified color.
     */
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * 读取assert下的txt文件
     *
     * @param fileName 文件名
     * @return 文本内容
     */
    public static String readStringFromAssert(Context context,String fileName) {
        return readStringFromAssert(context,fileName, "utf-8");
    }

    /**
     * 读取assert下的txt文件
     *
     * @param fileName     文件名
     * @param encodingCode 字符编码
     * @return 文本内容
     */
    public static String readStringFromAssert(Context context,String fileName, String encodingCode) {
        InputStream inputStream = null;
        try {
            inputStream = openAssetsFile(context,fileName);
            if (inputStream != null) {
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                return new String(buffer, encodingCode);
            }
        } catch (Exception e) {
            Log.e("xxx","",e);
        } finally {
            CloseUtils.closeIO(inputStream);
        }
        return "";
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     */
    public static InputStream openAssetsFile(Context context,String fileName) {
        try {
            return openAssetsFileWithException(context,fileName);
        } catch (IOException e) {
            Log.e("xxx","",e);
        }
        return null;
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     * @throws IOException
     */
    public static InputStream openAssetsFileWithException(Context context,String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

}
