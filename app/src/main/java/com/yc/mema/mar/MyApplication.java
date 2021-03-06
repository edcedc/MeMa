package com.yc.mema.mar;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bumptech.glide.request.target.ViewTarget;
import com.fm.openinstall.OpenInstall;
import com.nanchen.crashmanager.CrashApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.bugly.crashreport.CrashReport;
import com.yc.mema.R;
import com.yc.mema.service.InitializeService;

public class MyApplication extends CrashApplication {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
//        sApplication = this;
//        ViewTarget.setTagId(R.id.tag_glide);//项目glide 图片ID找不到  会报null
        InitializeService.start(this);

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        // 默认本地个性化地图初始化方法
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        CrashReport.initCrashReport(getApplicationContext(), "c22c5d4ca7", true);
    }

    public static Application getInstance(){
        return sApplication;
    }

    static  {
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
        //开始设置全局的基本参数（可以被下面的DefaultRefreshHeaderCreator覆盖）
            layout.setReboundDuration(1000);
            layout.setFooterHeight(100f);
            layout.setDisableContentWhenLoading(false);
            layout.setPrimaryColorsId(R.color.red_F58FB5, android.R.color.white);
            layout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
            layout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
            layout.setEnableNestedScroll(true);//是否启用嵌套滚动
        });
    }

}
