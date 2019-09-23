package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FEditAddressBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.EditAddressContract;
import com.yc.mema.presenter.EditAddressPresenter;
import com.yc.mema.utils.DatePickerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 17:00
 */
public class EditAddressFrg extends BaseFragment<EditAddressPresenter, FEditAddressBinding> implements EditAddressContract.View, View.OnClickListener {

    private String parentId;
    private String id = null;
    private DataBean bean;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_edit_address;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.add_address2));
        mB.tvLocation.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        EventBus.getDefault().register(this);

        if (bean != null){
            mB.etName.setText(bean.getUserName());
            mB.etPhone.setText(bean.getIphone());
            mB.tvLocation.setText(bean.getCounties());
            mB.etText.setText(bean.getAddress());
            parentId = bean.getCounty();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_location:
                UIHelper.startAddressFrg(this, AddressInEvent.HARVEST_ADDRESS);
//                DatePickerUtils.onAddressPicker(act);
                break;
            case R.id.bt_submit:
                mPresenter.onAdd(id, mB.etName.getText().toString(), mB.etPhone.getText().toString(), mB.tvLocation.getText().toString(), mB.etText.getText().toString(), parentId);
                break;
        }
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event){
        if (event.type != AddressInEvent.HARVEST_ADDRESS)return;
        parentId = event.parentId.split(",")[2];
        mB.tvLocation.setText(event.allAddress);
    }

    @Override
    public void setEdit() {
        pop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
