package com.yc.mema.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yc.mema.R;
import com.yc.mema.adapter.CustomizedAdapter;
import com.yc.mema.adapter.CustomizedChildAdapter;
import com.yc.mema.adapter.MealAdapter;
import com.yc.mema.adapter.MyPagerAdapter;
import com.yc.mema.adapter.TeaAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FBusinessGiftBinding;
import com.yc.mema.databinding.FCustomizedBinding;
import com.yc.mema.impl.CustomizedContract;
import com.yc.mema.presenter.CustomizedPresenter;
import com.yc.mema.utils.Constants;
import com.yc.mema.utils.MapUtils;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/17
 * Time: 17:46
 */
public class BusinessGiftDescFrg extends BaseFragment<CustomizedPresenter, FBusinessGiftBinding> implements CustomizedContract.View, View.OnClickListener, OnBannerListener {

    private DataBean bean;
    private String latitude;
    private String longitude;

    public static BusinessGiftDescFrg newInstance() {
        Bundle args = new Bundle();
        BusinessGiftDescFrg fragment = new BusinessGiftDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBannerBean = new ArrayList<>();
    private int type;

    private List<DataBean> listCakeBean = new ArrayList<>();
    private TeaAdapter cakeAdapter;

    private List<DataBean> listBean = new ArrayList<>();
    private CustomizedAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
        latitude = bean.getLatitude();
        longitude = bean.getLongitude();
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_business_gift;
    }

    @Override
    protected void initView(View view) {
        setSofia(false);
        mB.fyClose.setOnClickListener(this);
        mB.tvAddress.setOnClickListener(this);
        mB.tvPhone.setOnClickListener(this);
        mB.tvZh.setOnClickListener(this);
        mB.tvDistance.setOnClickListener(this);
        mB.tvSales.setOnClickListener(this);
        mB.tvScreen.setOnClickListener(this);
        mB.tvHigh.setOnClickListener(this);
        mB.toolbarLayout.setTitleEnabled(false);
        mB.toolbarLayout.setExpandedTitleGravity(Gravity.CENTER);//设置展开后标题的位置
        mB.toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mB.toolbarLayout.setExpandedTitleColor(Color.WHITE);//设置展开后标题的颜色
        mB.toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色
//        if (cakeAdapter == null){
//            cakeAdapter = new TeaAdapter(act, listCakeBean, 1);
//        }
//        mB.rvHot.setAdapter(cakeAdapter);
//        LinearLayoutManager slayoutManager = new LinearLayoutManager(act);
//        slayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mB.rvHot.setLayoutManager(slayoutManager);
//        mB.rvHot.setHasFixedSize(true);
//        mB.rvHot.setItemAnimator(new DefaultItemAnimator());
//        mB.rvHot.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.HORIZONTAL,  40, Color.parseColor("#ffffff")));
        if (adapter == null) {
            adapter = new CustomizedAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2));

        mB.tvTitle.setText(bean.getWalTitle());
        mB.ratingbar.setRating((float) bean.getScore());
        mB.tvNum.setText("100" +
                "/人");
        mB.tvTime.setText(bean.getBusinessTime());
        mB.tvPhone.setText(bean.getIphone());
        mB.tvAddress.setText(bean.getPcyAdd() + bean.getAddress());

        mB.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR);
        mB.banner.setImages(bean.getWelfareImgs())
                .setImageLoader(new OneGlideImageLoader())
                .setOnBannerListener(this)
                .setBannerAnimation(DefaultTransformer.class)
                .start();

        List<DataBean> setmealList = bean.getSetmealList();
        if (setmealList != null && setmealList.size() != 0) {
            mB.gpExp.setVisibility(View.VISIBLE);
            MealAdapter mealAdapter = new MealAdapter(act, setmealList);
            setRecyclerViewType(mB.rvHot);
            mB.rvHot.setAdapter(mealAdapter);
            mealAdapter.notifyDataSetChanged();
        }


        setToolBar();

        showLoadDataing();
        mPresenter.onHot();

        String context = bean.getContext();
        if (!StringUtils.isEmpty(context)) {
            mB.gpWeb.setVisibility(View.VISIBLE);
            mB.webView.loadDataWithBaseURL(null, bean.getContext(), "text/html", "utf-8", null);
            mB.webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    LogUtils.e(url);
                    return true;
                }

                @Override
                public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                    mB.progressBar.setVisibility(View.GONE);
                    ToastUtils.showShort("网页加载失败");
                }
            });
            //进度条
            mB.webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress == 100) {
                        mB.progressBar.setVisibility(View.GONE);
                        return;
                    }
                    mB.progressBar.setVisibility(View.VISIBLE);
                    mB.progressBar.setProgress(newProgress);
                }
            });
        }

        mB.refreshLayout.setEnableRefresh(false);
        mB.refreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                mPresenter.onRequest(pagerNumber = 1);
//                mB.refreshLayout.setEnableRefresh(false);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber += 1, type);
            }
        });
        setLabel(1);
    }

    /**
     * 初始化setmB.toolbar
     */
    private void setToolBar() {
        final AppCompatActivity mAppCompatActivity = (AppCompatActivity) act;
        mAppCompatActivity.setSupportActionBar(mB.toolbar);
        mAppCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mB.toolbar.setNavigationIcon(null);
        mB.toolbarLayout.setExpandedTitleGravity(Gravity.CENTER);//设置展开后标题的位置
        mB.toolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mB.toolbarLayout.setExpandedTitleColor(Color.WHITE);//设置展开后标题的颜色
        mB.toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色
        mB.appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            //verticalOffset  当前偏移量 appBarLayout.getTotalScrollRange() 最大高度 便宜值
            int Offset = Math.abs(verticalOffset); //目的是将负数转换为绝对正数；
            //标题栏的渐变
            mB.toolbar.setBackgroundColor(changeAlpha(getResources().getColor(R.color.white)
                    , Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange()));

            /**
             * 当前最大高度便宜值除以2 在减去已偏移值 获取浮动 先显示在隐藏
             */
            if (Offset < appBarLayout.getTotalScrollRange() / 2) {
                mB.topTitle.setText("");
                mB.toolbar.setAlpha(1);
                /**
                 * 从最低浮动开始渐显 当前 Offset就是  appBarLayout.getTotalScrollRange() / 2
                 * 所以 Offset - appBarLayout.getTotalScrollRange() / 2
                 */
            } else if (Offset > appBarLayout.getTotalScrollRange() / 2) {
                float floate = (Offset - appBarLayout.getTotalScrollRange() / 2) * 1.0f / (appBarLayout.getTotalScrollRange() / 2);
                mB.toolbar.setAlpha(floate);
                mB.topTitle.setText(mB.tvTitle.getText().toString());
                mB.toolbar.setAlpha(floate);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fy_close:
                act.finish();
                break;
            case R.id.tv_address:
                MapUtils.startMap(act, latitude, longitude, mB.tvTitle.getText().toString(), mB.tvAddress.getText().toString());
                break;
            case R.id.tv_zh:
                setLabel(1);
                break;
            case R.id.tv_distance:
                setLabel(2);
                break;
            case R.id.tv_sales:
                setLabel(3);
                break;
            case R.id.tv_high:
                setLabel(4);
                break;
            case R.id.tv_phone:
                String phone = mB.tvPhone.getText().toString();
                PopupWindowTool.showDialog(act)
                        .asConfirm("确定拨打" + phone + "?", "",
                                "取消", "确定",
                                () -> {
                                    if (ActivityCompat.checkSelfPermission(act, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    PhoneUtils.call(phone);
                                }, null, false)
                        .bindLayout(R.layout.p_dialog) //绑定已有布局
                        .show();
                break;
        }
    }


    /** 根据百分比改变颜色透明度 */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    @Override
    public void setBanner(List<DataBean> list) {

    }

    @Override
    public void setDesc(List<DataBean> list) {

    }

    @Override
    public void setHot(List<DataBean> list) {
//        listCakeBean.addAll(list);
//        cakeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mB.refreshLayout.finishRefresh();
        mB.refreshLayout.finishLoadMore();
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
    public void OnBannerClick(int position) {
//        DataBean bean = listBannerBean.get(position);

    }

    @Override
    public void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }

    private void setLabel(int type) {
        if (this.type == type) return;
        this.type = type;
        switch (type) {
            case 1://综合排序
                mB.tvZh.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvDistance.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvHigh.setTextColor(act.getResources().getColor(R.color.black_333333));
                break;
            case 2://距离
                mB.tvZh.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvDistance.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvHigh.setTextColor(act.getResources().getColor(R.color.black_333333));
                break;
            case 3://销量最高
                mB.tvZh.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvDistance.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvHigh.setTextColor(act.getResources().getColor(R.color.black_333333));
                break;
            case 4://好评
                mB.tvZh.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvDistance.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvHigh.setTextColor(act.getResources().getColor(R.color.red_F67690));
                break;
        }
        mPresenter.onRequest(pagerNumber = 1, type);
    }

}
