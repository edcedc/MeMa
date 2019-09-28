package com.yc.mema.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FApplyBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.impl.ApplyContract;
import com.yc.mema.presenter.ApplyPresenter;
import com.yc.mema.utils.CountDownTimerUtils;
import com.yc.mema.utils.DatePickerUtils;
import com.yc.mema.weight.PictureSelectorTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/19
 * Time: 15:19
 *  申请代理人
 */
public class ApplyFrg extends BaseFragment<ApplyPresenter, FApplyBinding> implements ApplyContract.View, View.OnClickListener {

    private String county;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    private List<DataBean> listBean = new ArrayList<>();
    private String roleId;
    private String imgZheng, imgFan, imgShou;

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_apply;
    }

    @Override
    protected void initView(View view) {
        mB.tvTitle.setOnClickListener(this);
        mB.tvCode.setOnClickListener(this);
        mB.tvType.setOnClickListener(this);
        mB.tvRegion.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.ivZheng.setOnClickListener(this);
        mB.ivFan.setOnClickListener(this);
        mB.ivShou.setOnClickListener(this);
        mPresenter.onRole();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title:
                pop();
                break;
            case R.id.tv_code:
                mPresenter.onCode(mB.etPhone.getText().toString());
                break;
            case R.id.tv_type:
                if (listBean.size() == 0)return;
                DatePickerUtils.onOptionPicker(act, listBean, (index, item) -> {
                    roleId = listBean.get(index).getRoleId();
                    mB.tvType.setText(listBean.get(index).getLabel());
                });
                break;
            case R.id.tv_region:
                UIHelper.startAddressAct(AddressInEvent.APPLY_TYPE);
                break;
            case R.id.iv_zheng:
                PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.USER_ZHENG, false);
                break;
            case R.id.iv_fan:
                PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.USER_FAN, false);
                break;
            case R.id.iv_shou:
                PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.USER_SHOU, false);
                break;
            case R.id.bt_submit:
                mPresenter.onSave(mB.etName.getText().toString(), mB.etWx.getText().toString(), mB.etPhone.getText().toString(),
                        mB.etCode.getText().toString(), mB.etInfocode.getText().toString(), roleId, mB.etIndustry.getText().toString(), county, mB.etDirect.getText().toString(),
                        mB.etMailbox.getText().toString(), imgZheng, imgFan, imgShou, mB.etId.getText().toString());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        if (event.getRequest() == CameraInEvent.USER_ZHENG){
            imgZheng = PictureSelector.obtainMultipleResult((Intent) event.getObject()).get(0).getPath();
            Glide.with(act).load(imgZheng).into(mB.ivZheng);
        }else if (event.getRequest() == CameraInEvent.USER_FAN){
            imgFan = PictureSelector.obtainMultipleResult((Intent) event.getObject()).get(0).getCompressPath();
            Glide.with(act).load(imgFan).into(mB.ivFan);
        }else if (event.getRequest() == CameraInEvent.USER_SHOU){
            imgShou = PictureSelector.obtainMultipleResult((Intent) event.getObject()).get(0).getCompressPath();
            Glide.with(act).load(imgShou).into(mB.ivShou);
        }
    }

    @Override
    public void setRole(List<DataBean> list) {
        listBean.addAll(list);
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event){
        county = event.parentId.split(",")[2];
        mB.tvRegion.setText(event.address);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
