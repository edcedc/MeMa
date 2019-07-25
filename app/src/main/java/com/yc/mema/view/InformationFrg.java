package com.yc.mema.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FInformationBinding;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;
import com.yc.mema.utils.DatePickerUtils;
import com.yc.mema.view.bottomFrg.CameraBottomFrg;
import com.yc.mema.weight.PictureSelectorTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 19:29
 *  完善信息
 */
public class InformationFrg extends BaseFragment<InformationPresenter, FInformationBinding> implements InformationContract.View, View.OnClickListener {

    private CameraBottomFrg cameraBottomFrg;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private String headPath;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_information;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.information));
        mB.tvHp.setOnClickListener(this);
        mB.ivHead.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        EventBus.getDefault().register(this);

        if (cameraBottomFrg == null){
            cameraBottomFrg = new CameraBottomFrg();
        }
        cameraBottomFrg.setCameraListener(new CameraBottomFrg.onCameraListener() {
            @Override
            public void camera() {
                PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.HEAD_CAMEAR, true);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

            @Override
            public void photo() {
                PictureSelectorTool.photo(act, CameraInEvent.HEAD_PHOTO, true);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

            @Override
            public void save() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        String path = localMediaList.get(0).getCompressPath();
        headPath = path;
        Glide.with(act).load(path).into(mB.ivHead);
        LogUtils.e(path);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_hp:
                DatePickerUtils.getYearMonthDayPicker(act, "选择生日", new DatePickerUtils.OnYearMonthDayListener() {
                    @Override
                    public void onTime(String year, String month, String day) {
                        mB.tvHp.setText(year + "-" + month + "-" + day);
                    }
                });
                break;
            case R.id.bt_submit:
//                mPresenter.submit(headPath, mB.etName.getText().toString(), mB.tvHp.getText().toString());
                UIHelper.startMainAct();
                break;
            case R.id.iv_head:
                cameraBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {

    }
}
