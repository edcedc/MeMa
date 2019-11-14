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

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundLinearLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CollectionAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSearchGiftBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.SearchGiftContract;
import com.yc.mema.presenter.SearchGiftPresenter;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
    private String parentId;
    private String location;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        parentId = bundle.getString("parentId");
        location = bundle.getString("location");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_search_gift;
    }

    @Override
    protected void initView(View view) {
        setSofia(false);
        EventBus.getDefault().register(this);
        etSearch = view.findViewById(R.id.et_search);
        RoundLinearLayout lySearch = view.findViewById(R.id.ly_search);
        lySearch.getDelegate().setBackgroundColor(act.getResources().getColor(R.color.white_f4f4f4));
        mB.tvLocation.setOnClickListener(this);
        mB.tvSearch.setOnClickListener(this);
        etSearch.setFocusable(true);
        etSearch.setFocusableInTouchMode(true);
        etSearch.setCursorVisible(true);

        if (adapter == null){
            adapter = new CollectionAdapter(act, listBean, 2);
        }
        setRecyclerViewType(mB.recyclerView, R.color.white);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  20));
        mB.recyclerView.setAdapter(adapter);

//        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(location, etSearch.getText().toString(), pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(location, etSearch.getText().toString(), pagerNumber += 1);
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
        mB.tvLocation.setText(location);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_location:
                UIHelper.startAddressAct(AddressInEvent.GIFT_TYPE);
                break;
            case R.id.tv_search:
                pop();
                break;
        }
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event){
        if (event.type != AddressInEvent.GIFT_TYPE)return;
        parentId = event.parentId;
        mB.tvLocation.setText(AddressBean.getInstance().getCity());
        location = mB.tvLocation.getText().toString();
        mB.refreshLayout.startRefresh();
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
