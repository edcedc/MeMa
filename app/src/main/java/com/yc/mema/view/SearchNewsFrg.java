package com.yc.mema.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundLinearLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CollectionAdapter;
import com.yc.mema.adapter.ThreeChildAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSearchNewsBinding;
import com.yc.mema.impl.SearchNewsContract;
import com.yc.mema.presenter.SearchNewsPresenter;
import com.yc.mema.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 22:07
 *  搜搜新闻
 */
public class SearchNewsFrg extends BaseFragment<SearchNewsPresenter, FSearchNewsBinding> implements SearchNewsContract.View, View.OnClickListener {

    private AppCompatEditText etSearch;
    private List<DataBean> listBean = new ArrayList<>();
    private ThreeChildAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_search_news;
    }

    @Override
    protected void initView(View view) {
        setSofia(false);
        etSearch = view.findViewById(R.id.et_search);
        RoundLinearLayout lySearch = view.findViewById(R.id.ly_search);
        lySearch.getDelegate().setBackgroundColor(act.getResources().getColor(R.color.white_f4f4f4));
        mB.tvSearch.setOnClickListener(this);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.setCursorVisible(true);

        if (adapter == null){
            adapter = new ThreeChildAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  20));
        mB.recyclerView.setAdapter(adapter);

//        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(etSearch.getText().toString(), pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(etSearch.getText().toString(), pagerNumber += 1);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_search:
                pop();
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
}
