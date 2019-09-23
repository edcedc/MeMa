package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CustomizedAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FChildCustimizedBinding;
import com.yc.mema.databinding.FCustomizedBinding;
import com.yc.mema.impl.CustomizedContract;
import com.yc.mema.presenter.CustomizedPresenter;
import com.yc.mema.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/18
 * Time: 1:00
 */
public class CustomizedChildFrg extends BaseFragment<CustomizedPresenter, FChildCustimizedBinding> implements CustomizedContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private CustomizedAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_child_custimized;
    }

    @Override
    protected void initView(View view) {
        if (adapter == null) {
            adapter = new CustomizedAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView, R.color.white);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));

        showLoadDataing();
        mPresenter.onRequest(pagerNumber = 1);
       /* mB.refreshLayout.setEnableRefresh(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });*/
    }

    @Override
    public void setBanner(List<DataBean> list) {

    }

    @Override
    public void setDesc(List<DataBean> list) {

    }

    @Override
    public void setHot(List<DataBean> list) {

    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
//        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
//        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
//            mB.refreshLayout.finishRefreshing();
        } else {
//            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
