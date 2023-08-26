package com.yuren.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;

import com.yongzemei.base.INoObscure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtil implements INoObscure {

    private BitmapUtil() {
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * @param image
     * @param size  以kb为单位
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int size) {
        ByteArrayInputStream isBm = new ByteArrayInputStream(compressImageToByte(image, size));// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    public static byte[] compressImageToByte(Bitmap image, int size) {
        if (image == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        int options = 90;
        int temp = 0;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

            temp = options - 10;// 每次都减少10
            if (temp < 3) {
                break;
            } else if (temp < 10) { // 每次减少10，所以最后不能小于10
                options -= 3;
            } else {
                options = temp;
            }
        }

        byte[] resutl = baos.toByteArray();

        try {
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resutl;
    }


    public static Bitmap getSuitableBg(Context context, int width, int height, int resId) {
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), resId);
        return getSuitableBg(width, height, decodeResource);
    }


    public static Bitmap getSuitableBg(int width, int height, Bitmap source) {
        int bgWidth = source.getWidth();
        int bgHeight = source.getHeight();
        float scaleWidth = (float) width / bgWidth;
        float scaleHeight = (float) height / bgHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBg = Bitmap.createBitmap(source, 0, 0, bgWidth, bgHeight, matrix, true);
        return newBg;
    }

    public static Drawable getDrawableByResourceId(Context context, int imgResourceId) {
        Resources res = context.getResources();

        // android 14的处理方案
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return res.getDrawable(imgResourceId);
        }

        // android 14之后用这个方法。 注意小米手机可能会缺失某些新api : getDrawableForDensity ( int id, int density, Theme theme )
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final int density = activityManager.getLauncherLargeIconDensity();
        if (density <= 0) {
            return res.getDrawable(imgResourceId);
        } else {
            return res.getDrawableForDensity(imgResourceId, density);
        }

    }

    public static Drawable getDrawableByResourceId(Activity context, int imgResourceId) {
        Resources res = context.getResources();

        // android 14的处理方案
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return res.getDrawable(imgResourceId);
        }

        // android 14之后用这个方法。 注意小米手机可能会缺失某些新api : getDrawableForDensity ( int id, int density, Theme theme )
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);


        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final int density = activityManager.getLauncherLargeIconDensity();
        if (density <= 0) {
            return res.getDrawable(imgResourceId);
        } else {
            return res.getDrawableForDensity(imgResourceId, density);
        }

    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {//取目标长宽的最大值来计算，这样会减少过度的尺寸压缩
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int suitedValue = reqHeight > reqWidth ? reqHeight : reqWidth;
            final int heightRatio = Math.round((float) height / (float) suitedValue);
            final int widthRatio = Math.round((float) width / (float) suitedValue);

            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static int calculateInSampleSize1(BitmapFactory.Options options, int reqSize) {
        return calculateInSampleSize1(options, reqSize, 100);
    }

    public static int calculateInSampleSize1(BitmapFactory.Options options, int reqSize, int smallSize) { //压缩比例  size：图片的压缩 到 size kb

        //图片的内存大小 格式565
        int imageSize = (options.outHeight * options.outWidth * 2) / 1024;
        int inSampleSize = 1;

        while (imageSize > reqSize) {

            inSampleSize += 1;

            imageSize /= (inSampleSize * inSampleSize);

            //得到的值,小于 最小值，不满足条件。 上方 inSmapleSize +1 ,需要 inSampleSize-1；
            if (imageSize < smallSize) {
                return --inSampleSize;
            }
        }


        return inSampleSize;
    }


    public static byte[] bitmap2Bytes(File file, int size, int smallSize) {
        if (file == null) {
            return null;
        }
        return bitmap2Bytes(file.getAbsolutePath(), size, smallSize);
    }

    public static Bitmap getUrl(File file, int size, int smallSize) {
        byte[] bitmapByte = bitmap2Bytes(file, size, smallSize);
        return BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
    }

    public static byte[] bitmap2Bytes(String file, int size, int smallSize) {
        return bitmap2Bytes(file, size, smallSize, null);
    }

    public static byte[] bitmap2Bytes(String file, int size, int smallSize, int[] wideHigh) {
        if (StringUtil.isEmpty(file)) {
            return null;
        }
        Bitmap sourceBitmap;


        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file, opts);


        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inSampleSize = BitmapUtil.calculateInSampleSize1(opts, size, smallSize);


        try {
            opts.inJustDecodeBounds = false;
            sourceBitmap = BitmapFactory.decodeFile(file, opts);
            //bitmap 为空，直接退出
            if(sourceBitmap==null){
                return null;
            }

            if (wideHigh != null) {
                wideHigh[0] = sourceBitmap.getWidth();
                wideHigh[1] = sourceBitmap.getHeight();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            //sourceBitmap =null;
            return null;
//            opts.inSampleSize = BitmapUtil.calculateInSampleSize1(opts,size/2);
//            opts.inJustDecodeBounds = false;
//            sourceBitmap = BitmapFactory.decodeFile(file,opts);
        }
        return compressImageToByte(sourceBitmap, size);
    }

    public static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;

        if (StringUtil.isEmpty(url)) {
            return null;
        }

        try {
            myFileUrl = new URL(url);

            HttpURLConnection conn;

            conn = (HttpURLConnection) myFileUrl.openConnection();

            conn.setDoInput(true);

            conn.connect();

            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);

        } catch (Throwable e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap returnUrlBitMap(final String url) {
        final Bitmap[] bitmap = {null};
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap[0] = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (Throwable e) {
                }
            }
        }).start();
        return bitmap[0];
    }


    public static Bitmap returnBitmap(String bitMapurl) {

        try {
            URL url = new URL(bitMapurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            int max = conn.getContentLength();
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] result = baos.toByteArray();
            return BitmapFactory.decodeByteArray(result, 0, result.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
