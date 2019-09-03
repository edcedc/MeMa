package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopCommentAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseListContract;
import com.yc.mema.base.BaseListPresenter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.BRecyclerBinding;
import com.yc.mema.impl.ShopDescContract;
import com.yc.mema.presenter.ShopDescPresenter;
import com.yc.mema.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/29
 * Time: 16:30
 *  商品全部评论
 */
public class ShopCommentFrg extends BaseFragment<ShopDescPresenter, BRecyclerBinding> implements ShopDescContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private ShopCommentAdapter adapter;
    private String id;

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
        return R.layout.b_recycler;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.user_evaluate));
        if (adapter == null){
            adapter = new ShopCommentAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView, R.color.white);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.recyclerView.setAdapter(adapter);

        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onComment(pagerNumber = 1, id);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onComment(pagerNumber += 1, id);
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
    public void setData(DataBean bean) {

    }

    @Override
    public void setCollState(int finalType) {

    }
}
