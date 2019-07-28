package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ComplaintAdapter;
import com.yc.mema.adapter.ReportNewsAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FReportNewsBinding;
import com.yc.mema.impl.ReportNewsContract;
import com.yc.mema.presenter.ReportNewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 22:24
 */
public class ReportNewsFrg extends BaseFragment<ReportNewsPresenter, FReportNewsBinding> implements ReportNewsContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private ReportNewsAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_report_news;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.mema7));
        mB.btSubmit.setOnClickListener(this);
        if (adapter == null){
            adapter = new ReportNewsAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new ReportNewsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                adapter.setPosition(position);
                adapter.notifyDataSetChanged();
            }
        });

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest( pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });
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

                break;
        }
    }
}
