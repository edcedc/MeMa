package com.yc.mema.view;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.blankj.utilcode.util.LogUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CollectionAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.User;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FCollectChildBinding;
import com.yc.mema.event.CollectionInEvent;
import com.yc.mema.impl.CollectionContract;
import com.yc.mema.presenter.CollectionPresenter;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 16:06
 */
public class CollectionChildFrg extends BaseFragment<CollectionPresenter, FCollectChildBinding> implements CollectionContract.View, View.OnClickListener {

    private int type = 0;

    private boolean isRequest = true;
    private List<DataBean> listBean = new ArrayList<>();
    private CollectionAdapter adapter;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
            isRequest = false;
            mB.refreshLayout.startRefresh();
        }
        for (DataBean bean : listBean){
            bean.setSelect(false);
            adapter.notifyDataSetChanged();
        }
        EventBus.getDefault().post(new CollectionInEvent(false, true));
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_collect_child;
    }

    @Override
    protected void initView(View view) {
        EventBus.getDefault().register(this);
        mB.tvDel.setOnClickListener(this);
        if (adapter == null){
            adapter = new CollectionAdapter(act, listBean, type);
        }
        switch (type){
            case 0:
                setRecyclerViewGridType(mB.recyclerView, 3, 10, 10, R.color.white);
                setMargins(mB.recyclerView, 30, 0, 30, 0);
                break;
            default:
                setRecyclerViewType(mB.recyclerView);
                mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
                break;
        }
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(type, pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(type, pagerNumber += 1);
            }
        });
        mB.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (DataBean bean : listBean){
                    bean.setSelect(b);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_del:
                List<DataBean> list = new ArrayList<>();
                for (DataBean bean : listBean){
                    LogUtils.e(bean.isSelect());
                    if (bean.isSelect()){
                        list.add(bean);
                    }
                }
                listBean.removeAll(list);
                adapter.notifyDataSetChanged();
                if (listBean.size() == 0){
                    mB.fYDel.setVisibility(View.GONE);
                    EventBus.getDefault().post(new CollectionInEvent(false, true));
                }
                break;
        }
    }

    @Subscribe
    public void CollectionInEvent(CollectionInEvent event){
        adapter.setDel(event.isCollection);
        adapter.notifyDataSetChanged();
        if (event.isCollection){
            mB.fYDel.setVisibility(View.VISIBLE);
        }else {
            mB.fYDel.setVisibility(View.GONE);
            for (DataBean bean : listBean){
                bean.setSelect(false);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
