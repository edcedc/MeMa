package com.yc.mema.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FTentryChildTwoBinding;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.event.TentryInEvent;
import com.yc.mema.impl.TentryContract;
import com.yc.mema.presenter.TentryPresenter;
import com.yc.mema.weight.PictureSelectorTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 20:22
 */
public class TentryChildTwoFrg extends BaseFragment<TentryPresenter, FTentryChildTwoBinding> implements TentryContract.View, View.OnClickListener {


    public static TentryChildTwoFrg newInstance() {

        Bundle args = new Bundle();

        TentryChildTwoFrg fragment = new TentryChildTwoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<LocalMedia> localMediaList = new ArrayList<>();
    private String weis;//卫生许可证
    private String ship;//食品许可证
    private String yingy;//营业执照
    private String sfz;//经营者身份证正面
    private String sff;//经营者身份证反面
    private String scsf;//手持身份证照片


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_tentry_child_two;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.ivImg1.setOnClickListener(this);
        mB.ivImg2.setOnClickListener(this);
        mB.ivImg3.setOnClickListener(this);
        mB.ivImg4.setOnClickListener(this);
        mB.ivImg5.setOnClickListener(this);
        mB.ivImg6.setOnClickListener(this);
        mB.btShang.setOnClickListener(this);
        mB.btXia.setOnClickListener(this);
        EventBus.getDefault().register(this);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
        SpannableString hText = new SpannableString(getString(R.string.mema38));
        hText.setSpan(colorSpan, 0, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        hText.setSpan(new AbsoluteSizeSpan(15, true), 9, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mB.tvText.setText(hText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_img1:
                PictureSelectorTool.PictureSelectorImage(act,  CameraInEvent.yingy, false, false);
                break;
            case R.id.iv_img2:
                PictureSelectorTool.PictureSelectorImage(act,  CameraInEvent.scsf, false, false);
                break;
            case R.id.iv_img3:
                PictureSelectorTool.PictureSelectorImage(act,  CameraInEvent.sfz, false, false);
                break;
            case R.id.iv_img4:
                PictureSelectorTool.PictureSelectorImage(act,  CameraInEvent.sff, false, false);
                break;
            case R.id.iv_img5:
                PictureSelectorTool.PictureSelectorImage(act,  CameraInEvent.weis, false, false);
                break;
            case R.id.iv_img6:
                PictureSelectorTool.PictureSelectorImage(act,  CameraInEvent.ship, false, false);
                break;
            case R.id.bt_shang:
                EventBus.getDefault().post(new TentryInEvent(1, 2));
                break;
            case R.id.bt_xia:
                mPresenter.onTwoSure(yingy, scsf, sfz, sff, weis, ship);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        String path = localMediaList.get(0).getPath();
//        Glide.with(act).load(path).into(mB.ivHead);
        switch (event.getRequest()) {
            case CameraInEvent.yingy:
                Glide.with(act).load(path).into(mB.ivImg1);
                yingy = path;
                break;
            case CameraInEvent.scsf:
                Glide.with(act).load(path).into(mB.ivImg2);
                scsf = path;
                break;
            case CameraInEvent.sfz:
                Glide.with(act).load(path).into(mB.ivImg3);
                sfz = path;
                break;
            case CameraInEvent.sff:
                Glide.with(act).load(path).into(mB.ivImg4);
                sff = path;
                break;
            case CameraInEvent.weis:
                Glide.with(act).load(path).into(mB.ivImg5);
                weis = path;
                break;
            case CameraInEvent.ship:
                Glide.with(act).load(path).into(mB.ivImg6);
                ship = path;
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setData(List<DataBean> list) {

    }
}
