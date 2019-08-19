package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.adapter.AddressAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.AddressBean;
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
    private StringBuffer sbId = new StringBuffer();
    private String addressEnd = "";
    private String parentId;
    private boolean isUpdate = false;
    private int regionLevel;
    private int type;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        isUpdate = bundle.getBoolean("isUpdate");
        type = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_address;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_address), getString(R.string.submit1));
        mB.tvLocation.setText(AddressBean.getInstance().getAddress().city);
        if (adapter == null){
            adapter = new AddressAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        mPresenter.onRequest(null);
        adapter.setOnClickListener((parentId, address, regionLevel, position) -> {
            mB.gpLocate.setVisibility(View.GONE);
            mB.tvAll.setText(sb.toString());
            this.regionLevel = regionLevel;
            if (regionLevel >= 3){
                this.addressEnd = address;
                this.parentId = parentId;
                adapter.setmPosition(position);
                adapter.notifyDataSetChanged();
            }else {
                sb.append(address).append(" ");
                sbId.append(parentId).append(",");
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
            sbId.append(parentId);
            if (sbId.toString().indexOf("edison") != -1){
                sbId = sbId.delete(sbId.toString().length() - 7, sbId.toString().length());
                addressEnd = sb.toString().split(" ")[1];
            }
            LogUtils.e(sbId.toString());
            EventBus.getDefault().post(new AddressInEvent(sbId.toString(), addressEnd, type));
            pop();
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
        if (regionLevel == 2 && type == 0){
            DataBean bean = new DataBean();
            bean.setRegionName("不限区域");
            bean.setRegionLevel(3);
            bean.setRegionId("edison");
            listBean.add(0, bean);
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveUser() {
        pop();
    }
}
