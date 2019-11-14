package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

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
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 *  商城
 */
public class TwoFrg extends BaseFragment<TwoPresenter, FTwoBinding> implements TwoContract.View, View.OnClickListener, OnBannerListener {

    public static TwoFrg newInstance() {
        Bundle args = new Bundle();
        TwoFrg fragment = new TwoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private int weightPosition = -1;

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
        mB.fyClose.setOnClickListener(this);
        mB.tvArrival.setOnClickListener(this);
        mB.tvSales.setOnClickListener(this);
        mB.tvPopular.setOnClickListener(this);
        if (adapter == null) {
            adapter = new ShopAdapter(act, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 2, 40, 40, R.color.white_f4f4f4);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        setLabel(0);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, weightPosition, null, 0, 0, null);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, weightPosition, null, 0, 0, null);
            }
        });
        mPresenter.onBanner();
        mPresenter.onLabel();
        mPresenter.onWeight();
        labelAdapter = new ShopLabelAdapter(act, listLabelBean);
        mB.rvLabel.setAdapter(labelAdapter);
        mB.rvLabel.setOnItemClickListener((adapterView, view1, i, l) -> {
            DataBean bean = listLabelBean.get(i);
            if (bean.getTitle().equals(act.getString(R.string.more))){
                UIHelper.startMoreCategoryFrg(this);
            }else {
                UIHelper.startCategoryFrg(this, bean.getId(), bean.getTitle());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fy_close:
                act.finish();
                break;
            case R.id.et_search:
                UIHelper.startSearchShopFrg(this);
                break;
            case R.id.tv_arrival:
                setLabel(2);
                break;
            case R.id.tv_sales:
                setLabel(1);
                break;
            case R.id.tv_popular:
                setLabel(0);
                break;
        }
    }

    private void setLabel(int type){
        if (this.weightPosition == type)return;
        this.weightPosition = type;
        switch (type){
            case 2://最新上架
                mB.tvPopular.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvArrival.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                break;
            case 1://销量最高
                mB.tvPopular.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvArrival.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.red_F67690));
                break;
            case 0://热门推荐
                mB.tvPopular.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvArrival.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                break;
        }
        mB.refreshLayout.startRefresh();
    }

    @Override
    public void setBanner(List<DataBean> list) {
        listBannerBean.addAll(list);
        mB.banner.setImages(listBannerBean)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
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
       /* mB.flLabel.removeAllViews();
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
        });*/
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
