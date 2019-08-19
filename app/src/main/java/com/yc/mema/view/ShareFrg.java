package com.yc.mema.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.TimeUtils;
import com.google.zxing.WriterException;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.databinding.FShareBinding;
import com.yc.mema.utils.ImageUtils;
import com.yc.mema.weight.ZXingUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/19
 * Time: 12:08
 *  分享推广
 */
public class ShareFrg extends BaseFragment<BasePresenter, FShareBinding> implements View.OnClickListener {

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_share;
    }

    @Override
    protected void initView(View view) {
        final AppCompatActivity mAppCompatActivity = (AppCompatActivity) act;
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(v -> act.onBackPressed());

        mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mB.ivZking.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Bitmap bitmap = null;
                try {
                    bitmap = ZXingUtils.creatBarcode("https://www.baidu.com/", 100);
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:

                break;
        }
    }
}
