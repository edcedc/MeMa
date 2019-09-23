package com.yc.mema.view.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.LogUtils;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.google.zxing.WriterException;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.databinding.FShareBinding;
import com.yc.mema.utils.ImageUtils;
import com.yc.mema.utils.ShareTool;
import com.yc.mema.weight.ZXingUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/20
 * Time: 11:56
 */
public class ShareAct extends BaseActivity<BasePresenter, FShareBinding> implements View.OnClickListener {

//    private String shareUrl = "https://app-u52vwi.openinstall.io/js-test/android/8339877980916172211?testKey=testValue";
    private ShareAction shareAction;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_share;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        setSofia(false);
        final AppCompatActivity mAppCompatActivity = (AppCompatActivity) act;
        Toolbar toolbar = findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(v -> act.onBackPressed());

        mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mB.ivZking.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Bitmap bitmap = null;
                try {
                    bitmap = ZXingUtils.creatBarcode(CloudApi.SHARE_URL, 100);
                    if (bitmap != null) {
                        mB.ivZking.setImageBitmap(bitmap);
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        mB.btSubmit.setOnClickListener(this);
        mB.layout.setOnLongClickListener(view1 -> {
            ImageUtils.viewSaveToImage(act, mB.layout);
            showToast("保存成功");
            return false;
        });
        //获取唤醒参数
        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);
        shareAction = ShareTool.getInstance(act).shareAction(CloudApi.SHARE_URL);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter);
    }

    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取渠道数据
            String channelCode = appData.getChannel();
            //获取绑定数据
            String bindData = appData.getData();
            LogUtils.e("OpenInstall", "getWakeUp : wakeupData = " + appData.toString());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wakeUpAdapter = null;
        UMShareAPI.get(this).release();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                shareAction.open();
                break;
        }
    }

}
