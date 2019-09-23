package com.yc.mema.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.StringUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FSearchShopBinding;
import com.yc.mema.impl.TwoContract;
import com.yc.mema.presenter.TwoPresenter;
import com.yc.mema.utils.PopupWindowTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/28
 * Time: 11:47
 *  搜索商品
 */
public class SearchShopFrg extends BaseFragment<TwoPresenter, FSearchShopBinding> implements TwoContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private ShopAdapter adapter;
    private int type;
    private int di, gao;
    private AppCompatEditText etSearch;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_search_shop;
    }

    @Override
    protected void initView(View view) {
        etSearch = view.findViewById(R.id.et_search);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.setCursorVisible(true);
        mB.tvArrival.setOnClickListener(this);
        mB.tvSales.setOnClickListener(this);
        mB.tvPrice.setOnClickListener(this);
        mB.tvScreen.setOnClickListener(this);
        mB.fyClose.setOnClickListener(this);
        if (adapter == null) {
            adapter = new ShopAdapter(act, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 2, 40, 40, R.color.white_f4f4f4);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
//        setLabel(2);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1, type, null, di, gao, etSearch.getText().toString());
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1, type, null, di, gao, etSearch.getText().toString());
            }
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            //判断是否是“完成”键
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                if (StringUtils.isEmpty(etSearch.getText().toString())){
                    showToast(act.getString(R.string.mema8));
                    return false;
                }
                mB.refreshLayout.startRefresh();
                return true;
            }
            return false;
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fy_close:
                pop();
                break;
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
                    SearchShopFrg.this.di = di;
                    SearchShopFrg.this.gao = gao;
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

    @Override
    public void setBanner(List<DataBean> list) {

    }

    @Override
    public void setLabel(List<DataBean> list) {

    }

    @Override
    public void setWeight(List<DataBean> list) {

    }
}
