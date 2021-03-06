package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CollectionAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FOneBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.OneContract;
import com.yc.mema.presenter.OnePresenter;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.view.act.HtmlAct;
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
    int[] item = {2 ,3 ,4, 5, 6, 7, 1, 0};

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
//        mPresenter.onLabel();
        mB.rvLabel.setOnItemClickListener((adapterView, view1, i, l) -> {
            this.itemize = item[i];
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

        mB.tvLocation.setText(AddressBean.getInstance().getCity());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_search:
                UIHelper.startSearchGiftFrg(this, parentId, mB.tvLocation.getText().toString());
                break;
            case R.id.tv_location:
                UIHelper.startAddressAct(AddressInEvent.GIFT_TYPE);
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
        int type = bean.getType();
        if (type == 2){
            UIHelper.startHtmlAct(HtmlAct.ABOUT, bean.getAbort());
        }else if (type == 3){
            UIHelper.startGiftAct(bean.getAbort());
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mB.banner.startAutoPlay();
        String city = AddressBean.getInstance().getDistrict();
        if (city != null && !city.equals(mB.tvLocation.getText().toString())){
//            mB.tvLocation.setText(city);
//            mB.refreshLayout.startRefresh();
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mB.banner.stopAutoPlay();
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event){
        if (event.type != AddressInEvent.GIFT_TYPE)return;
        parentId = event.parentId;
        mB.tvLocation.setText(AddressBean.getInstance().getCity());
        mB.refreshLayout.startRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
