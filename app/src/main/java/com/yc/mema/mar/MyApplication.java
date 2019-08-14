package com.yc.mema.mar;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.request.target.ViewTarget;
import com.nanchen.crashmanager.CrashApplication;
import com.yc.mema.R;
import com.yc.mema.service.InitializeService;

public class MyApplication extends CrashApplication {

    private static Application sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        ViewTarget.setTagId(R.id.tag_glide);//项目glide 图片ID找不到  会报null
        InitializeService.start(this);
    }

    public static Application getInstance(){
        return sApplication;
    }


}
