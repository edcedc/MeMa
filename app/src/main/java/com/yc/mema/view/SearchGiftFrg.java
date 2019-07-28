package com.yc.mema.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSearchGiftBinding;
import com.yc.mema.impl.SearchGiftContract;
import com.yc.mema.presenter.SearchGiftPresenter;
import com.yc.mema.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 20:55
 *  搜索礼包
 */
public class SearchGiftFrg extends BaseFragment<SearchGiftPresenter, FSearchGiftBinding> implements SearchGiftContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private CollectionAdapter adapter;
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
        return R.layout.f_search_gift;
    }

    @Override
    protected void initView(View view) {
        setSofia(false);
        etSearch = view.findViewById(R.id.et_search);
        RoundLinearLayout lySearch = view.findViewById(R.id.ly_search);
        lySearch.getDelegate().setBackgroundColor(act.getColor(R.color.white_f4f4f4));
        mB.tvLocation.setOnClickListener(this);
        mB.tvSearch.setOnClickListener(this);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.setCursorVisible(true);

        if (adapter == null){
            adapter = new CollectionAdapter(act, listBean, 2);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  20));
        mB.recyclerView.setAdapter(adapter);

        mB.refreshLayout.startRefresh();
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
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
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
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (editable.length() == 0){
                            mB.tvSearch.setText(getText(R.string.search));
                        }else {
                            mB.tvSearch.setText(getText(R.string.cancel));
                        }
                    }
                }, 300);
            }
        });
        mB.tvLocation.setText("广州");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_location:
                UIHelper.startAddressFrg(this, 1);
                break;
            case R.id.tv_search:
                if (mB.tvSearch.getText().toString().equals(getText(R.string.cancel))){
                    mB.tvSearch.setText(getText(R.string.search));
                    etSearch.setText("");
                }else {
                    if (StringUtils.isEmpty(etSearch.getText().toString())){
                        showToast(act.getString(R.string.mema8));
                        return;
                    }
                    mB.refreshLayout.startRefresh();
                    mB.tvSearch.setText(getText(R.string.cancel));
                }
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