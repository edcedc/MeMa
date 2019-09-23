package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ConfirmOrdersAdapter;
import com.yc.mema.adapter.ThreeChildAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.BNotTitleRecyclerBinding;
import com.yc.mema.databinding.BRecyclerBinding;
import com.yc.mema.event.RefreshOrderListInEvent;
import com.yc.mema.impl.OrderContract;
import com.yc.mema.presenter.OrderPresenter;
import com.yc.mema.utils.Constants;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 21:40
 */
public class OrderChildFrg extends BaseFragment<OrderPresenter, BNotTitleRecyclerBinding> implements OrderContract.View {

    private int type;
    private List<DataBean> listBean = new ArrayList<>();
    private ConfirmOrdersAdapter adapter;

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
            isRequest = false;
            showLoadDataing();
            mB.refreshLayout.startRefresh();
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.b_not_title_recycler;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        if (adapter == null) {
            adapter = new ConfirmOrdersAdapter(act, this, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView, R.color.white);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  20));
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, type);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, type);
            }
        });
        adapter.setOnClickListener(new ConfirmOrdersAdapter.OnClickListener() {
            @Override
            public void onConfirm(int position, String goodId) {
                PopupWindowTool.showDialog(act)
                        .asConfirm("确定收货吗？", "",
                                "取消", "确定",
                                () -> {
                                    mPresenter.onUpdateOrder(position, goodId, Constants.successDeliver);
                                }, null, false)
                        .bindLayout(R.layout.p_dialog) //绑定已有布局
                        .show();
            }

            @Override
            public void onCallOrder(int position, String goodId) {
                PopupWindowTool.showDialog(act)
                        .asConfirm("确定取消订单吗？", "",
                                "取消", "确定",
                                () -> {
                                    mPresenter.onUpdateOrder(position, goodId, Constants.waitHarvest);
                                }, null, false)
                        .bindLayout(R.layout.p_dialog) //绑定已有布局
                        .show();
            }
        });
        EventBus.getDefault().register(this);
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

    @Subscribe
    public void onMainRefreshOrderListInEvent(RefreshOrderListInEvent event){
        mB.refreshLayout.startRefresh();
    }

    @Override
    public void setRefresh(int position) {
        listBean.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemChanged(position);
        if (listBean.size() == 0){
            showLoadEmpty();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
