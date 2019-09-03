package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopCommentAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FShopBinding;
import com.yc.mema.impl.ShopDescContract;
import com.yc.mema.presenter.ShopDescPresenter;
import com.yc.mema.utils.GlideImageLoader;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.view.bottomFrg.ShopSkuBottonFrg;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/28
 * Time: 11:50
 * 商品详情
 */
public class ShopDescFrg extends BaseFragment<ShopDescPresenter, FShopBinding> implements ShopDescContract.View, View.OnClickListener {

    private String id;
    private int isTrue;

    public static ShopDescFrg newInstance() {
        Bundle args = new Bundle();
        ShopDescFrg fragment = new ShopDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private ShopSkuBottonFrg shopSkuBottonFrg;
    private List<DataBean> listBean = new ArrayList<>();
    private ShopCommentAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_shop;
    }

    @Override
    protected void initView(View view) {
        mB.fyClose.setOnClickListener(this);
        mB.tvSku.setOnClickListener(this);
        mB.tvCustomer.setOnClickListener(this);
        mB.tvCollection.setOnClickListener(this);
        mB.tvImmediately.setOnClickListener(this);
        mB.tvLook.setOnClickListener(this);
        shopSkuBottonFrg = new ShopSkuBottonFrg();
        mPresenter.onRequest(id);
        mPresenter.onComment(pagerNumber = 1, id);
        if (adapter == null){
            adapter = new ShopCommentAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.recyclerView.setAdapter(adapter);
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

        mB.banner.updateBannerStyle(BannerConfig.NUM_INDICATOR);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fy_close:
                act.finish();
                break;
            case R.id.tv_sku:
                shopSkuBottonFrg.show(getChildFragmentManager(), "dialog");
                break;
            case R.id.tv_customer://客服

                break;
            case R.id.tv_collection://收藏
                mPresenter.onColl(id, isTrue);
                break;
            case R.id.tv_immediately://立即购买
                UIHelper.startImmediatelyFrg(this);
                break;
            case R.id.tv_look:
                UIHelper.startShopCommentFrg(this, id);
                break;
        }
    }

    @Override
    public void setData(DataBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString("bean", new Gson().toJson(bean));
        shopSkuBottonFrg.setArguments(bundle);
        List<DataBean> goodSupImgs = bean.getGoodSupImgs();
        mB.banner.setImages(goodSupImgs)
                .setImageLoader(new GlideImageLoader())
                .start();
        setCollState(bean.getIsTrue());
        double price = bean.getPrice();
        SpannableString hText = new SpannableString("¥" + price);
        hText.setSpan(new AbsoluteSizeSpan(8, true), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvPrice.setText(hText);
        mB.tvTitle.setText(bean.getGoodName());
        mB.tvSales.setText("已售" + bean.getSales() + "件");

        mB.webView.loadDataWithBaseURL(null, bean.getRemark(), "text/html", "utf-8", null);
    }

    @Override
    public void setCollState(int isTrue) {
        this.isTrue = isTrue;
        if (isTrue == 0) {
            mB.tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    act.getResources().getDrawable(R.mipmap.staroutline, null), null, null);
        } else {
            mB.tvCollection.setCompoundDrawablesWithIntrinsicBounds(null,
                    act.getResources().getDrawable(R.mipmap.shoucang1, null), null, null);
        }
    }

    @Override
    public void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mB.banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mB.banner.stopAutoPlay();
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
//        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        mB.recyclerView.setVisibility(View.VISIBLE);
        List<DataBean> list = (List<DataBean>) data;
        mB.tvEvaluate.setText("宝贝评价（" +
                list.size() +
                "）");
        if (pagerNumber == 1) {
            listBean.clear();
//            mB.refreshLayout.finishRefreshing();
        } else {
//            mB.refreshLayout.finishLoadmore();
        }
        listBean.add(list.get(0));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
//        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }


}
