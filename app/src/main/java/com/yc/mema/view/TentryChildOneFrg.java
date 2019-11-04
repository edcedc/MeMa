package com.yc.mema.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FTentryChildOneBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.event.BusinessCassificationInEvent;
import com.yc.mema.event.TentryInEvent;
import com.yc.mema.impl.TentryContract;
import com.yc.mema.presenter.TentryPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 15:39
 */
public class TentryChildOneFrg extends BaseFragment<TentryPresenter, FTentryChildOneBinding> implements TentryContract.View, View.OnClickListener {

    private String classifyId;

    public static TentryChildOneFrg newInstance() {
        
        Bundle args = new Bundle();
        
        TentryChildOneFrg fragment = new TentryChildOneFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private String parentId;

    private int type;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_tentry_child_one;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.btSubmit.setOnClickListener(this);
        mB.tvAddress.setOnClickListener(this);
        mB.tvCategory.setOnClickListener(this);
        EventBus.getDefault().register(this);
        mB.cbShop.setOnCheckedChangeListener((compoundButton, b) -> {

        });
         mB.cbMerchants.setOnCheckedChangeListener((compoundButton, b) -> mB.gpCategory.setVisibility(b ? View.VISIBLE : View.GONE));
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_address:
                UIHelper.startAddressAct(AddressInEvent.TENTRY);
                break;
            case R.id.bt_submit:
                mPresenter.onSure(mB.etName.getText().toString(), mB.etPhone.getText().toString(), mB.etOperatorId.getText().toString(), mB.etOperatorNum.getText().toString(), mB.etOpeningBank.getText().toString(),
                        mB.etReservePhone.getText().toString(), mB.etBankNum.getText().toString(), parentId, mB.etAddress.getText().toString(),
                        type, classifyId, mB.etStoreArea.getText().toString(), mB.etBusinessScope.getText().toString(),
                        mB.cbShop.isChecked(), mB.cbMerchants.isChecked());
                break;
            case R.id.tv_category:
                UIHelper.startShopCategoryFrg(this);
                break;
        }
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event){
        if (event.type != AddressInEvent.TENTRY)return;
        parentId = event.parentId;
        mB.tvAddress.setText(AddressBean.getInstance().getAddress());
    }

    @Subscribe
    public void onMainBusinessCassificationInEvent(BusinessCassificationInEvent event){
        classifyId = event.classifyId;
        mB.tvCategory.setText(event.title);
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
