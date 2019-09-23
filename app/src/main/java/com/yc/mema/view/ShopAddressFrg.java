package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopAddressAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FShopAddressBinding;
import com.yc.mema.event.ShopAddressInEvent;
import com.yc.mema.impl.SetAddressContract;
import com.yc.mema.presenter.SetAddressPresenter;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 16:28
 *  我的收获地址
 */
public class ShopAddressFrg extends BaseFragment<SetAddressPresenter, FShopAddressBinding> implements SetAddressContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private ShopAddressAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_shop_address;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.add_address2));
        mB.btSubmit.setOnClickListener(this);
        if (adapter == null) {
            adapter = new ShopAddressAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        showLoadDataing();
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }
        });
        adapter.setOnClickListener(new ShopAddressAdapter.OnClickListener() {
            @Override
            public void onEdit(String id, int position, DataBean bean) {
//                mPresenter.onEdit(id, position);
                UIHelper.startEditAddressFrg(ShopAddressFrg.this, id, bean);
            }

            @Override
            public void onDel(String id, int position) {
                PopupWindowTool.showDialog(act)
                        .asConfirm("确定删除吗？", "",
                        "取消", "确定",
                                () -> {
                                    mPresenter.onDel(id, position);
                                }, null, false)
                        .bindLayout(R.layout.p_dialog) //绑定已有布局
                        .show();;
            }

            @Override
            public void onMoRen(String id, int position) {
                mPresenter.onMoRen(id, position);
            }
        });

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mB.refreshLayout.startRefresh();
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                UIHelper.startEditAddressFrg(this, null, null);
                break;
        }
    }

    @Override
    public void setMoRen(int position) {
        for (int i = 0;i<listBean.size();i++){
            DataBean bean = listBean.get(i);
            if (i == position){
                bean.setStatus(2);
                EventBus.getDefault().post(new ShopAddressInEvent(bean));
            }else {
                bean.setStatus(1);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDel(int position) {
        listBean.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemChanged(position);
    }
}
