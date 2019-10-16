package com.yc.mema.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/11
 * Time: 13:31
 */
public class MapUtils {

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    private static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    private static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);//lng
        double lat = z * Math.sin(theta);//lat
        return new LatLng(lat, lng);
    }

    /**
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    private static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }


    public static void startMap(Activity act, String la, String lo, String title, String address){
        if (StringUtils.isEmpty(la) || StringUtils.isEmpty(lo)){
            ToastUtils.showShort("经纬度无效");
            return;
        }
        double latitude = Double.valueOf(la);
        double longitude = Double.valueOf(lo);
        Intent intent = new Intent();
        //如果已安装,
        if(MapUtils.isAvilible(act,"com.baidu.BaiduMap")) {//传入指定应用包名
            intent.setData(Uri.parse("baidumap://map/marker?location=" +
                    latitude +
                    "," +
                    longitude +
                    "&title=" + title +
                    "&content= " + address +
                    "&traffic=on&src=andr.baidu.openAPIdemo"));
        }else if(MapUtils.isAvilible(act,"com.autonavi.minimap")){
            LatLng endPoint = MapUtils.BD2GCJ(new LatLng(latitude, longitude));//坐标转换
            StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=").append("amap");
            stringBuffer.append("&lat=").append(endPoint.latitude)
                    .append("&lon=").append(endPoint.longitude).append("&keywords=" + address)
                    .append("&dev=").append(0)
                    .append("&style=").append(2);
            intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
            intent.setPackage("com.autonavi.minimap");
        }else if(MapUtils.isAvilible(act,"com.tencent.map")){
            LatLng endPoint = MapUtils.BD2GCJ(new LatLng(latitude, longitude));//坐标转换
            StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive")
                    .append("&tocoord=").append(endPoint.latitude).append(",").append(endPoint.longitude).append("&to=" + address);
            intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()));
        }else {
            ToastUtils.showShort("请安装第三方地图方可导航");
            return;
        }
        act.startActivity(intent);
    }

}
