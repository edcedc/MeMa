package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yc.mema.R;
import com.yc.mema.adapter.CustomizedAdapter;
import com.yc.mema.adapter.TeaAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FBusinessDescBinding;
import com.yc.mema.impl.BusinessDescContract;
import com.yc.mema.presenter.BusinessDescPresenter;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 19:03
 *  商家二级页面
 */
public class BusinessDescFrg extends BaseFragment<BusinessDescPresenter, FBusinessDescBinding> implements BusinessDescContract.View, OnBannerListener {

    public static BusinessDescFrg newInstance() {

        Bundle args = new Bundle();

        BusinessDescFrg fragment = new BusinessDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private CustomizedAdapter adapter;

    private List<DataBean> listBannerBean = new ArrayList<>();
    private List<DataBean> listTeaBean = new ArrayList<>();
    private TeaAdapter teaAdapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_business_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle("二级页面");
        if (adapter == null) {
            adapter = new CustomizedAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));

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
                mPresenter.onRequest( pagerNumber += 1);
            }
        });
    }

    @Override
    public void setBanner(List<DataBean> list) {
        listBannerBean.addAll(list);
//        mB.banner.setImages(listBannerBean)
//                .setImageLoader(new OneGlideImageLoader())
//                .setOnBannerListener(this)
//                .setBannerAnimation(DefaultTransformer.class)
//                .start();
    }

    @Override
    public void setTea(List<DataBean> list) {
        listTeaBean.addAll(list);
        teaAdapter.notifyDataSetChanged();
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
    public void OnBannerClick(int position) {
        DataBean bean = listBannerBean.get(position);

    }
}
