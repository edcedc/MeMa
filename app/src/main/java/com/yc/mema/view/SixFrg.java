package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yc.mema.R;
import com.yc.mema.adapter.CustomizedAdapter;
import com.yc.mema.adapter.HomeClassifyAdapter;
import com.yc.mema.adapter.ShopLabelAdapter;
import com.yc.mema.adapter.TeaAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSixBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.SixContract;
import com.yc.mema.presenter.SixPresenter;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.view.PopupView.PSortView;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/11
 * Time: 15:42
 */
public class SixFrg extends BaseFragment<SixPresenter, FSixBinding> implements SixContract.View, View.OnClickListener, OnBannerListener {

    private String parentId;

    public static SixFrg newInstance() {

        Bundle args = new Bundle();

        SixFrg fragment = new SixFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBannerBean = new ArrayList<>();
    private List<DataBean> listBannerAdvBean = new ArrayList<>();

    private List<DataBean> listTeaBean = new ArrayList<>();
    private TeaAdapter teaAdapter;
    private List<DataBean> listCakeBean = new ArrayList<>();
    private TeaAdapter cakeAdapter;

    private List<DataBean> listHomeBean = new ArrayList<>();
    private HomeClassifyAdapter classifyAdapter;
    private List<DataBean> listBean = new ArrayList<>();
    private CustomizedAdapter adapter;

    private ShopLabelAdapter labelAdapter;
    private List<DataBean> listLabelBean = new ArrayList<>();

    private int type;
    private int low;
    private int up;
    private String county;

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
        EventBus.getDefault().register(this);
        mB.tvLocation.setOnClickListener(this);
        mB.tvZh.setOnClickListener(this);
        mB.tvDistance.setOnClickListener(this);
        mB.tvSales.setOnClickListener(this);
        mB.tvScreen.setOnClickListener(this);
        mB.tvSearch.setOnClickListener(this);
        mB.tvLocation.setText(AddressBean.getInstance().getDistrict());

        labelAdapter = new ShopLabelAdapter(act, listLabelBean);
        mB.gridView.setAdapter(labelAdapter);
        mB.gridView.setOnItemClickListener((adapterView, view1, i, l) -> {
            DataBean bean = listLabelBean.get(i);
            switch (i) {
                case 4:
                    UIHelper.startShopAct();
                    break;
                default:
                    UIHelper.startBusinessDescAct(bean.getClassifyId(), bean.getClassifyTitle());
                    break;
            }
        });

//        if (classifyAdapter == null) {
//            classifyAdapter = new HomeClassifyAdapter(act, listHomeBean);
//        }
//        mB.rvDesc.setAdapter(classifyAdapter);
//        setRecyclerViewType(mB.rvDesc);
//        mB.rvDesc.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));


        if (teaAdapter == null) {
            teaAdapter = new TeaAdapter(act, listTeaBean, 1);
        }
        mB.rvTea.setAdapter(teaAdapter);
        LinearLayoutManager tlayoutManager = new LinearLayoutManager(act);
        tlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mB.rvTea.setLayoutManager(tlayoutManager);
        mB.rvTea.setHasFixedSize(true);
        mB.rvTea.setItemAnimator(new DefaultItemAnimator());
        mB.rvTea.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.HORIZONTAL, 40, Color.parseColor("#ffffff")));

        if (cakeAdapter == null) {
            cakeAdapter = new TeaAdapter(act, listCakeBean, 2);
        }
        mB.rvCake.setAdapter(cakeAdapter);
        LinearLayoutManager slayoutManager = new LinearLayoutManager(act);
        slayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mB.rvCake.setLayoutManager(slayoutManager);
        mB.rvCake.setHasFixedSize(true);
        mB.rvCake.setItemAnimator(new DefaultItemAnimator());
        mB.rvCake.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.HORIZONTAL, 40, Color.parseColor("#ffffff")));



        if (adapter == null) {
            adapter = new CustomizedAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2));

        showLoadDataing();
        mPresenter.onBanner();
        mPresenter.onLabel();
        county = AddressBean.getInstance().getCity();
        mPresenter.onRequest(pagerNumber = 1, low, up, type, county);
        mPresenter.onGetHomeClassify(null);
        mB.refreshLayout.setEnableRefresh(false);
        mB.refreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mPresenter.onRequest(pagerNumber = 1);
//                mB.refreshLayout.setEnableRefresh(false);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber += 1, low, up, type, county);
            }
        });
        setLabel(1);
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
    public void setFree(List<DataBean> list) {
        listTeaBean.addAll(list);
        teaAdapter.notifyDataSetChanged();
    }

    @Override
    public void setCake(List<DataBean> list) {
        listCakeBean.addAll(list);
        cakeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBannerAdv(List<DataBean> list) {
        listBannerAdvBean.addAll(list);
        mB.bannerAdv.setImages(listBannerBean)
                .setImageLoader(new OneGlideImageLoader())
                .setOnBannerListener(this)
                .setBannerAnimation(DefaultTransformer.class)
                .start();
    }

    @Override
    public void setLabel(List<DataBean> list) {
        listLabelBean.addAll(list);
        labelAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
//        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
        if (listBean.size() == totalRow) {
            mB.refreshLayout.setEnableLoadMore(false);
        } else {
            mB.refreshLayout.setEnableLoadMore(true);
        }
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mB.refreshLayout.finishRefresh();
        mB.refreshLayout.finishLoadMore();
//        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                UIHelper.startAddressAct(AddressInEvent.LIWU);
                break;
            case R.id.tv_zh:
//                setLabel(1);
                PopupWindowTool.showSort(act, mB.tvZh).setOnClickListener((text, type) -> {
                    LogUtils.e(text, type);
                    mB.tvZh.setText(text);
                    mPresenter.onRequest(pagerNumber = 1, low, up, type, county);
                    setLabel(1);
                });
                break;
            case R.id.tv_distance:
                setLabel(2);
                break;
            case R.id.tv_sales:
                setLabel(3);
                break;
            case R.id.tv_screen:
                PopupWindowTool.showShopPriceScreen(act, mB.tvZh, (di, gao) -> {
                    SixFrg.this.low = di;
                    SixFrg.this.up = gao;
                    mPresenter.onRequest(pagerNumber = 1, low, up, type, county);
                });
                break;
            case R.id.tv_search:
                UIHelper.startSearchGiftFrg(this, null, AddressBean.getInstance().getCity());
                break;
        }
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event) {
        if (event.type != AddressInEvent.LIWU) return;
        county = AddressBean.getInstance().getCity();
        mB.tvLocation.setText(AddressBean.getInstance().getCity());
        mPresenter.onRequest(pagerNumber = 1, 0, 0, 1, county);
        setLabel(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setLabel(int type) {
        if (this.type == type) return;
        this.type = type;
        switch (type) {
            case 1://综合排序
                mB.tvZh.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvDistance.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                break;
            case 2://距离
                mB.tvZh.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvDistance.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                break;
            case 3://销量最高
                mB.tvZh.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvDistance.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.red_F67690));
                break;
        }
        mPresenter.onRequest(pagerNumber = 1, low, up, type, county);
    }

}
