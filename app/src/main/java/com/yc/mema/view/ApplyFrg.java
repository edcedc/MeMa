package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FApplyBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.ApplyContract;
import com.yc.mema.presenter.ApplyPresenter;
import com.yc.mema.utils.DatePickerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_apply;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.apply1));
        mB.lyType.setOnClickListener(this);
        mB.lyAddress.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mPresenter.onRole();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_type:
                if (listBean.size() == 0)return;
                DatePickerUtils.onOptionPicker(act, listBean, (index, item) -> {
                    roleId = listBean.get(index).getRoleId();
                    mB.tvType.setText(listBean.get(index).getLabel());
                });
                break;
            case R.id.ly_address:
                UIHelper.startAddressFrg(this, 1, AddressInEvent.APPLY_TYPE);
                break;
            case R.id.bt_submit:
                mPresenter.onSaveAgent(mB.etName.getText().toString(), mB.etPhone.getText().toString(), roleId, county, mB.etText.getText().toString());
                break;
        }
    }

    @Override
    public void setRole(List<DataBean> list) {
        listBean.addAll(list);
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event){
        county = event.parentId.split(",")[2];
        mB.tvAddress.setText(event.address);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
