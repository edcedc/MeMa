package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CategoryAdapter;
import com.yc.mema.adapter.CategoryShopAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FCategoryBinding;
import com.yc.mema.impl.CategoryContract;
import com.yc.mema.presenter.CategoryPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/27
 * Time: 15:39
 *  更多分类
 */
public class CategoryFrg extends BaseFragment<CategoryPresenter, FCategoryBinding> implements CategoryContract.View {

    private List<DataBean> listCatBean = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private List<DataBean> listBean = new ArrayList<>();
    private CategoryShopAdapter adapter;
    private String id;
    private String title;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_category;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.shop_category));
        categoryAdapter = new CategoryAdapter(act, listCatBean);
        mB.listView.setAdapter(categoryAdapter);
        mB.listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            String parentId = listCatBean.get(i).getCategoryId();
            String categoryName = listCatBean.get(i).getCategoryName();
            if (parentId != id){
                id = parentId;
                this.title = categoryName;
                mB.tvTitle.setText(title);
                categoryAdapter.setmPosition(i);
                categoryAdapter.notifyDataSetChanged();
                mB.refreshLayout.startRefresh();
            }
        });
        mPresenter.onRequest("0", pagerNumber = 1);
        if (adapter == null) {
            adapter = new CategoryShopAdapter(act, this, listBean);
        }
        setRecyclerViewGridType(mB.recyclerView, 3, 40, 20, R.color.white);
        mB.recyclerView.setAdapter(adapter);
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(id, pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(id, pagerNumber += 1);
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
    public void setLabel(List<DataBean> list, String categoryId, String name) {
        listCatBean.clear();
        listCatBean.addAll(list);
        categoryAdapter.notifyDataSetChanged();

        this.id = categoryId;
        this.title = name;
        mB.tvTitle.setText(title);
        categoryAdapter.setmPosition(0);
        categoryAdapter.notifyDataSetChanged();
        mB.refreshLayout.startRefresh();
    }

}
