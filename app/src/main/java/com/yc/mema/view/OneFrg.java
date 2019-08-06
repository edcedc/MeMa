package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CollectionAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FOneBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.OneContract;
import com.yc.mema.presenter.OnePresenter;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class OneFrg extends BaseFragment<OnePresenter, FOneBinding> implements OneContract.View, View.OnClickListener, OnBannerListener {

    public static OneFrg newInstance() {
        Bundle args = new Bundle();
        OneFrg fragment = new OneFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBannerBean = new ArrayList<>();

    private List<DataBean> listBean = new ArrayList<>();
    private CollectionAdapter adapter;
    private String searchText = null;
    private String parentId;

    private int itemize;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_one;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        setSofia(false);
        EventBus.getDefault().register(this);
        view.findViewById(R.id.et_search).setOnClickListener(this);
        mB.tvLocation.setOnClickListener(this);

        if (adapter == null){
            adapter = new CollectionAdapter(act, listBean, 2);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  20));
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        mPresenter.onBanner();
        mPresenter.onGridView(this, mB.rvLabel);
        mB.rvLabel.setOnItemClickListener((adapterView, view1, i, l) -> {
            this.itemize = i;
            mB.refreshLayout.startRefresh();
        });
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(parentId == null ? mB.tvLocation.getText().toString() : parentId, searchText, itemize, pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(parentId == null ? mB.tvLocation.getText().toString() : parentId, searchText, itemize, pagerNumber += 1);
            }
        });

        mB.tvLocation.setText("广州");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_search:
                UIHelper.startSearchGiftFrg(this, parentId, mB.tvLocation.getText().toString());
                break;
            case R.id.tv_location:
                UIHelper.startAddressFrg(this, 0);
                break;
        }
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
    public void setBanner(List<DataBean> list) {
        listBannerBean.addAll(list);
        mB.banner.setImages(listBannerBean)
                .setImageLoader(new OneGlideImageLoader())
                .setOnBannerListener(this)
                .setBannerAnimation(DefaultTransformer.class)
                .start();
    }

    @Override
    public void OnBannerClick(int position) {
        DataBean bean = listBannerBean.get(position);

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mB.banner.startAutoPlay();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mB.banner.stopAutoPlay();
    }

    @Subscribe
    public void AddressInEvent(AddressInEvent event){
        parentId = event.getParentId();
        mB.tvLocation.setText(event.getAddress());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
