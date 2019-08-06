package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.adapter.AddressAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FAddressBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 12:01
 *  设置地址
 */
public class AddressFrg extends BaseFragment<InformationPresenter, FAddressBinding> implements InformationContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private AddressAdapter adapter;
    private StringBuffer sb = new StringBuffer();
    private String addressEnd = "";
    private String parentId;
    private boolean isUpdate = false;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        isUpdate = bundle.getBoolean("isUpdate");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_address;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_address), getString(R.string.submit1));
        if (adapter == null){
            adapter = new AddressAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        mPresenter.onRequest(null);
        mB.tvLocation.setText("广州");
        adapter.setOnClickListener((parentId, address, regionLevel) -> {
            mB.gpLocate.setVisibility(View.GONE);
            mB.tvAll.setText(sb.toString());
            if (regionLevel >= 3){
                this.addressEnd = address;
            }else {
                sb.append(address);
                mPresenter.onRequest(parentId);
            }
            mB.tvAll.setText(sb.toString() + addressEnd);
            this.parentId = parentId;
        });
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        if (StringUtils.isEmpty(addressEnd))return;
        if (!isUpdate){
            sb.append(addressEnd);
            EventBus.getDefault().post(new AddressInEvent(parentId, addressEnd));
        }else {
            mPresenter.address(parentId);
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        listBean.clear();
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveUser() {
        pop();
    }
}
