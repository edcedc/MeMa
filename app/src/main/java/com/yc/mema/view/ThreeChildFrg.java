package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ThreeChildAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FThreeChildBinding;
import com.yc.mema.impl.ThreeChildContract;
import com.yc.mema.presenter.ThreeChildPresenter;
import com.yc.mema.utils.GlideImageLoader;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 21:30
 */
public class ThreeChildFrg extends BaseFragment<ThreeChildPresenter, FThreeChildBinding> implements ThreeChildContract.View, OnBannerListener {

    private List<DataBean> listBannerBean = new ArrayList<>();

    private List<DataBean> listBean = new ArrayList<>();
    private ThreeChildAdapter adapter;
    private String id;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
            isRequest = false;
            showLoadDataing();
            mPresenter.onBanner();
            mB.refreshLayout.startRefresh();
        }
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_three_child;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        if (adapter == null) {
            adapter = new ThreeChildAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(id, pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(id, pagerNumber += 1);
            }
        });
    }

    @Override
    public void setBanner(List<DataBean> list) {
        listBannerBean.addAll(list);
        mB.banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .setBannerAnimation(DefaultTransformer.class).start();
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
        int type = bean.getType();
        if (type == 2){
            UIHelper.startHtmlAct(HtmlAct.ABOUT, bean.getAbort());
        }else if (type == 3){
            UIHelper.startNewsDescAct(bean.getAbort(), null);
        }else {

        }
    }
}
