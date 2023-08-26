package com.yuren.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期: 2018/11/28 on 2018/11/28
 * Created by 陈 chenjuan
 * 描述：判断是否安装某一软件
 */

public class AppInstalledUtils {

    /**
     * 腾讯地图 Uri 标识
     */
    private final static String BASE_URL = "qqmap://map/";

    //进入路径规划
    public static void openMap(Context context, double lat, double lng, String address,String title) {
        // 高德
        if (AppInstalledUtils.isAppInstalled(context, "com.autonavi.minimap")) {
            //打开高德路线规划
            openGaoDeMap(context, lat, lng, address);

        } else if (AppInstalledUtils.isAppInstalled(context, "com.baidu.BaiduMap")) {
            //百度
            openBaiDuMap(context, lat, lng, address,title);
        } else if (AppInstalledUtils.isInstalled()) {
            //腾讯
            AppInstalledUtils.invokeNavi(context, "drive", null, null, null, address, String.valueOf(lat) + "," + String.valueOf(lng), null, "textApp");
        } else if (AppInstalledUtils.isAppInstalled(context, "com.google.android.apps.maps")) {
            //google 网页版的
            openGoogleMap(context, lat, lng, address);
        } else {
            /*//百度网页
            //开始定位当前位置
            MapProxy.getInstance().startLocation(this);*/
            gotoMapAPP(String.valueOf(lat), String.valueOf(lng), address, context);
        }

    }

    /**
     * 打开高德
     */
    private static void openGaoDeMap(Context context, double lat, double lng, String address) {
        try {

            String url = "androidamap://route?sourceApplication=softname&sname=我的位置&dlat=" + lat + "&dlon=" + lng + "&dname=" + address + "&dev=0&t=0";
            Uri mUri = Uri.parse(url);

            //Uri mUri = Uri.parse("amapuri://route/plan/?sid=BGVIS1&slat=" +lat + "&slon=" + lng + "&sname=" +"我的位置" + "&did=BGVIS2&dlat="+locBody.getLatitude()+"&dlon="+locBody.getLongitude()+"&dname="+locBody.getAddress()+"&dev=0&t=0");
            Intent intent = new Intent(Intent.ACTION_VIEW, mUri);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            context.startActivity(intent);
        } catch (Throwable e) {

        }
    }

    /**
     * 打开百度地图的驾车路线规划界面
     */
    private static void openBaiDuMap(Context context, double lat, double lng, String address,String title) {
        /*try {
            Intent intent = new Intent();

            if (lat != 4.9E-324 && lng != 4.9E-324) {
                intent.setData(Uri.parse("baidumap://map/direction?region=湖南&origin=" + lat + "," + lng + "&destination=" + address + "&mode=driving"));
            } else {
                intent.setData(Uri.parse("baidumap://map/direction?region=湖南&origin=" + address + "&destination=" + address + "&mode=driving"));
            }
            context.startActivity(intent);
        } catch (Throwable e) {

        }*/

        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse("bdapp://map/marker?location="+lat+"," +
                    lng+"&title="+title+"&content="+address+"&traffic=on&src=andr.youmiankeji.youmian&coord_type=gcj02"));

           /* if (lat != 4.9E-324D && lng != 4.9E-324D) {
                intent.setData(Uri.parse("baidumap://map/direction?region=河南&origin=" + lat + "," + lng + "&destination=" + address + "&mode=driving"));
            } else {
                intent.setData(Uri.parse("baidumap://map/direction?region=河南&origin=" + address + "&destination=" + address + "&mode=driving"));
            }*/

            context.startActivity(intent);
        } catch (Throwable var7) {
            ;
        }

    }

    /**
     * 打开google地图的驾车路线规划界面
     */
    private static void openGoogleMap(Context context, double lat, double lng, String address) {
        try {
            StringBuffer stringBuffer = new StringBuffer("google.navigation:q=").append(lat).append(",").append(lng).append("&mode=d");
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(stringBuffer.toString()));
            i.setPackage("com.google.android.apps.maps");
            context.startActivity(i);
        } catch (Throwable e) {

        }
    }

    //检查包名
    private static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
        //存储所有已安装程序的包名
        List<String> pName = new ArrayList<>();
        //从info中将报名字逐一取出
        if (pInfo != null) {
            for (int i = 0; i < pInfo.size(); i++) {
                String pn = pInfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    // 判断是否安装腾讯地图app
    private static boolean isInstalled() {
        return new File("/data/data/com.tencent.map").exists();
    }

    /**
     * 调用腾讯地图app驾车导航
     * (此处输入方法执行任务.)
     * <h3>Version</h3> 1.0
     * <h3>CreateTime</h3> 2017/11/9,15:31
     * <h3>UpdateTime</h3> 2017/11/9,15:31
     * <h3>CreateAuthor</h3>
     * <h3>UpdateAuthor</h3>
     * <h3>UpdateInfo</h3> (此处输入修改内容,若无修改可不写.)
     *
     * @param context
     * @param from       选 出发地址
     * @param fromcoord  选 出发经纬度   移动端如果起点名称和起点坐标均未传递，则使用当前定位位置作为起点 如 39.9761,116.3282
     * @param to         必 目标地址
     * @param tocoord    必 目标经纬度 39.9761,116.3282
     * @param policy     选  本参数取决于type参数的取值
     *                   公交：type=bus，policy有以下取值 0：较快捷 1：少换乘 2：少步行 3：不坐地铁
     *                   驾车：type=drive，policy有以下取值 0：较快捷 1：无高速 2：距离 policy的取值缺省为0
     * @param coord_type 选 坐标类型，取值如下：1 GPS  2 腾讯坐标（默认）  如果用户指定该参数为非腾讯地图坐标系，则URI API自动进行坐标处理，以便准确对应到腾讯地图底图上。
     * @param type       必 公交：bus  驾车：drive  步行：walk（仅适用移动端）
     * @param referer    必  调用来源，一般为您的应用名称，为了保障对您的服务，请务必填写！
     */
    private static void invokeNavi(Context context, @NonNull String type, String coord_type, String from,
                                   String fromcoord, @NonNull String to, @NonNull String tocoord, String policy, @NonNull String referer) {

        try {
            StringBuffer stringBuffer = new StringBuffer(BASE_URL)
                    .append("routeplan?")
                    .append("type=")
                    .append(type)
                    .append("&to=")
                    .append(to)
                    .append("&tocoord=")
                    .append(tocoord)
                    .append("&referer=")
                    .append(referer);
            if (!TextUtils.isEmpty(from)) {
                stringBuffer.append("&from=").append(from);
            }
            if (!TextUtils.isEmpty(fromcoord)) {
                stringBuffer.append("&fromcoord=").append(fromcoord);
            }
            if (!TextUtils.isEmpty(policy)) {
                stringBuffer.append("&policy=").append(policy);
            }
            if (!TextUtils.isEmpty(coord_type)) {
                stringBuffer.append("&coord_type=").append(coord_type);
            }
            Intent intent = new Intent();
            intent.setData(Uri.parse(stringBuffer.toString()));
            context.startActivity(intent);
        } catch (Throwable e) {

        }
    }


    public static void gotoMapAPP(String jing, String wei, String address, Context context) {//进入地图

        //所有为空，不执行操作
        if (StringUtil.isEmpty(jing) && StringUtil.isEmpty(wei) && StringUtil.isEmpty(address)) {
            return;
        }
        try {
            Uri mapUri = null;
            if (StringUtil.isEmpty(jing) || StringUtil.isEmpty(wei)) {
                mapUri = Uri.parse(StringUtil.getAddressUrl(address));
            } else {
                mapUri = Uri.parse(StringUtil.getAddressDetailUrl(wei, jing, address));
            }
            Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
            context.startActivity(loction);
        } catch (ActivityNotFoundException e) {
            ToastMaster.makeText(context, "请确认安装了地图App", Toast.LENGTH_LONG);
            gotoHtmlMapAPP(jing, wei, address, context);
        } catch (Exception e) {
            ToastMaster.makeText(context, "启动异常", Toast.LENGTH_LONG);
        }
    }

    private static void gotoHtmlMapAPP(String jing, String wei, String address, Context context) {
        try {
            Uri mapUri = null;
            if (StringUtil.isEmpty(jing) || StringUtil.isEmpty(wei)) {
                mapUri = Uri.parse(StringUtil.getHtmlAddressUrl(address));
            } else {
                mapUri = Uri.parse(StringUtil.getHtmlAddressDetailUrl(wei, jing, address));
            }
            Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
            context.startActivity(loction);
        } catch (Exception e) {
            ToastMaster.makeText(context, "打开网页版地图异常", Toast.LENGTH_LONG);
        }
    }

}
