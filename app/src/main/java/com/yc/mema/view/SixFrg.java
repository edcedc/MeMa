package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CollectionAdapter;
import com.yc.mema.adapter.CustomizedAdapter;
import com.yc.mema.adapter.CustomizedChildAdapter;
import com.yc.mema.adapter.MyPagerAdapter;
import com.yc.mema.adapter.TeaAdapter;
import com.yc.mema.adapter.ThreeChildAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSixBinding;
import com.yc.mema.impl.SixContract;
import com.yc.mema.presenter.SixPresenter;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/11
 * Time: 15:42
 */
public class SixFrg extends BaseFragment<SixPresenter, FSixBinding> implements SixContract.View, View.OnClickListener, OnBannerListener {

    public static SixFrg newInstance() {

        Bundle args = new Bundle();

        SixFrg fragment = new SixFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBannerBean = new ArrayList<>();

    private List<DataBean> listTeaBean = new ArrayList<>();
    private TeaAdapter teaAdapter;
    private List<DataBean> listCakeBean = new ArrayList<>();
    private TeaAdapter cakeAdapter;
    private List<DataBean> listBean = new ArrayList<>();
    private CustomizedChildAdapter adapter;

    private String[] titles = new String[]{"热门"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_six;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        setSofia(false);
        mB.tvPresent.setOnClickListener(this);
//        fragments.add(new CustomizedChildFrg());
//        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fragments, titles));
//        mB.tbLayout.setViewPager(mB.viewPager);
//        mB.viewPager.setOffscreenPageLimit(0);
//        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                mB.viewPager.setCurrentItem(position);
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//            }
//        });
        if (teaAdapter == null) {
            teaAdapter = new TeaAdapter(act, listTeaBean, 0);
        }
        mB.rvTea.setAdapter(teaAdapter);
        LinearLayoutManager tlayoutManager = new LinearLayoutManager(act);
        tlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mB.rvTea.setLayoutManager(tlayoutManager);
        mB.rvTea.setHasFixedSize(true);
        mB.rvTea.setItemAnimator(new DefaultItemAnimator());
        mB.rvTea.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.HORIZONTAL, 40, Color.parseColor("#ffffff")));
        if (cakeAdapter == null) {
            cakeAdapter = new TeaAdapter(act, listCakeBean, 1);
        }
        mB.rvCake.setAdapter(cakeAdapter);
        LinearLayoutManager slayoutManager = new LinearLayoutManager(act);
        slayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mB.rvCake.setLayoutManager(slayoutManager);
        mB.rvCake.setHasFixedSize(true);
        mB.rvCake.setItemAnimator(new DefaultItemAnimator());
        mB.rvCake.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.HORIZONTAL, 40, Color.parseColor("#ffffff")));

//        if (adapter == null) {
//            adapter = new CustomizedAdapter(act, listBean);
//        }
//        mB.recyclerView.setAdapter(adapter);
//        setRecyclerViewType(mB.recyclerView);
//        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));

        adapter = new CustomizedChildAdapter(act, listBean);
        mB.listView.setAdapter(adapter);

        showLoadDataing();
//        mB.refreshLayout.setPureScrollModeOn();
        mPresenter.onBanner();
        mPresenter.onTea();
        mPresenter.onCake();
        mPresenter.onRequest(pagerNumber = 1);

        mB.refreshLayout.setEnableRefresh(false);
//        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//            }
//        });

        GlideLoadingUtils.load(act, "https://wx3.sinaimg.cn/mw690/78a9167dgy1g6vqt47zrqj211s0b47mz.jpg", mB.ivImg);
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
    public void setTea(List<DataBean> list) {
        listTeaBean.addAll(list);
        teaAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCake(List<DataBean> list) {
        listCakeBean.addAll(list);
        cakeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
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
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_present:
                UIHelper.startShopAct();
                break;
        }
    }
}
