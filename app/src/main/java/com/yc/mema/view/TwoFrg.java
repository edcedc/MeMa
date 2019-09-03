package com.yc.mema.view;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopAdapter;
import com.yc.mema.adapter.ShopLabelAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FTwoBinding;
import com.yc.mema.impl.TwoContract;
import com.yc.mema.presenter.TwoPresenter;
import com.yc.mema.utils.OneGlideImageLoader;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

public class TwoFrg extends BaseFragment<TwoPresenter, FTwoBinding> implements TwoContract.View, View.OnClickListener, OnBannerListener {

    public static TwoFrg newInstance() {
        Bundle args = new Bundle();
        TwoFrg fragment = new TwoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private int weightPosition = 0;

    private List<DataBean> listBannerBean = new ArrayList<>();
    private List<DataBean> listLabelBean = new ArrayList<>();
    private ShopLabelAdapter labelAdapter;
    private List<DataBean> listBean = new ArrayList<>();
    private ShopAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_two;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        setSofia(false);
        view.findViewById(R.id.et_search).setOnClickListener(this);

        if (adapter == null) {
            adapter = new ShopAdapter(act, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 2, 60, 60, R.color.white_f4f4f4);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, weightPosition);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, weightPosition);
            }
        });
        mPresenter.onBanner();
        mPresenter.onLabel();
        mPresenter.onWeight();
        labelAdapter = new ShopLabelAdapter(act, listLabelBean);
        mB.rvLabel.setAdapter(labelAdapter);
        mB.rvLabel.setOnItemClickListener((adapterView, view1, i, l) -> {
            DataBean bean = listLabelBean.get(i);
            if (bean.getId().equals("1")){
                UIHelper.startCategoryFrg(this);
            }else {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_search:
                UIHelper.startSearchShopFrg(this);
                break;
        }
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
    public void setLabel(List<DataBean> list) {
        listLabelBean.clear();
        listLabelBean.addAll(list);
        labelAdapter.notifyDataSetChanged();
    }

    @Override
    public void setWeight(List<DataBean> list) {
        mB.flLabel.removeAllViews();
        TagAdapter<DataBean> adapter = new TagAdapter<DataBean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, DataBean bean) {
                View view = View.inflate(act, R.layout.i_shop_weight, null);
                AppCompatTextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(bean.getTitle());
                return view;
            }
        };
        mB.flLabel.setAdapter(adapter);
        adapter.setSelectedList(0);
        mB.flLabel.setOnTagClickListener((view, position, parent) -> {
            if (weightPosition != position){
                weightPosition = position;
                mB.refreshLayout.startRefresh();
            }
            return true;
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
    public void OnBannerClick(int position) {
        DataBean bean = listBannerBean.get(position);
        int type = bean.getType();
        if (type == 2) {
//            UIHelper.startHtmlAct(HtmlAct.ABOUT, bean.getAbort());
        } else if (type == 3) {
//            UIHelper.startGiftAct(bean.getAbort());
        }
    }

}
