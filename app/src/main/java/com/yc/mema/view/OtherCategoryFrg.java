package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FOtherCategoryBinding;
import com.yc.mema.impl.TwoContract;
import com.yc.mema.presenter.TwoPresenter;
import com.yc.mema.utils.PopupWindowTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/5
 * Time: 11:17
 *  各种分类
 */
public class OtherCategoryFrg extends BaseFragment<TwoPresenter, FOtherCategoryBinding> implements TwoContract.View, View.OnClickListener {

    private String title;
    private String categoryId;

    private List<DataBean> listBean = new ArrayList<>();
    private ShopAdapter adapter;
    private int type;
    private int di, gao;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        title = bundle.getString("title");
        categoryId = bundle.getString("categoryId");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_other_category;
    }

    @Override
    protected void initView(View view) {
        setTitle(title);
        mB.tvArrival.setOnClickListener(this);
        mB.tvSales.setOnClickListener(this);
        mB.tvPrice.setOnClickListener(this);
        mB.tvScreen.setOnClickListener(this);
        if (adapter == null) {
            adapter = new ShopAdapter(act, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 2, 40, 40, R.color.white_f4f4f4);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        setLabel(2);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, type, categoryId, di, gao, null);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, type, categoryId, di, gao, null);
            }
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
    public void setBanner(List<DataBean> list) {

    }

    @Override
    public void setLabel(List<DataBean> list) {

    }

    @Override
    public void setWeight(List<DataBean> list) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_arrival:
                setLabel(2);
                break;
            case R.id.tv_sales:
                setLabel(1);
                break;
            case R.id.tv_price:
                if (type == 3){
                    setLabel(4);
                }else {
                    setLabel(3);
                }
                break;
            case R.id.tv_screen:
                PopupWindowTool.showShopPriceScreen(act, mB.tvArrival, (di, gao) -> {
                    OtherCategoryFrg.this.di = di;
                    OtherCategoryFrg.this.gao = gao;
                    mB.refreshLayout.startRefresh();
                });
                break;
        }
    }

    private void setLabel(int type){
        if (this.type == type)return;
        this.type = type;
        switch (type){
            case 2://最新上架
                mB.tvArrival.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvPrice.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvPrice.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, act.getResources().getDrawable(R.mipmap.datatransferboth, null), null);
                break;
            case 1://销量最高
                mB.tvArrival.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvPrice.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvPrice.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, act.getResources().getDrawable(R.mipmap.datatransferboth, null), null);
                break;
            case 3://价格升
                mB.tvArrival.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvPrice.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvPrice.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, act.getResources().getDrawable(R.mipmap.sheng1, null), null);
                break;
            case 4://价格降
                mB.tvArrival.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvSales.setTextColor(act.getResources().getColor(R.color.black_333333));
                mB.tvPrice.setTextColor(act.getResources().getColor(R.color.red_F67690));
                mB.tvPrice.setCompoundDrawablesWithIntrinsicBounds(null,
                        null, act.getResources().getDrawable(R.mipmap.jiang1, null), null);
                break;
        }
        mB.refreshLayout.startRefresh();
    }

}
