package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopCategoryLeftAdapter;
import com.yc.mema.adapter.ShopCategoryRightAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FShopCategoryBinding;
import com.yc.mema.event.BusinessCassificationInEvent;
import com.yc.mema.event.ShopAddressInEvent;
import com.yc.mema.impl.ShopCategoryContract;
import com.yc.mema.presenter.ShopCategoryPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 20:24
 */
public class ShopCategoryFrg extends BaseFragment<ShopCategoryPresenter, FShopCategoryBinding> implements ShopCategoryContract.View, View.OnClickListener {


    private List<DataBean> listLeftBean = new ArrayList<>();
    private ShopCategoryLeftAdapter categoryLeftAdapter;

    private List<DataBean> listRightBean = new ArrayList<>();
    private ShopCategoryRightAdapter categoryRightAdapter;

    private String classifyId;
    private String categoryName;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_shop_category;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.business_category));
        mB.btSubmit.setOnClickListener(this);
        categoryLeftAdapter = new ShopCategoryLeftAdapter(act, listLeftBean);
        mB.listView.setAdapter(categoryLeftAdapter);
        mB.listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            String parentId = listLeftBean.get(i).getClassifyId();
            categoryName = listLeftBean.get(i).getClassifyTitle();
            if (parentId != classifyId){
                classifyId = parentId;
                mB.refreshLayout.startRefresh();
                mB.tvText.setText(categoryName);
                categoryLeftAdapter.setmPosition(i);
                categoryLeftAdapter.notifyDataSetChanged();
                categoryRightAdapter.setmPosition(-1);
                categoryRightAdapter.notifyDataSetChanged();
            }
        });
        mPresenter.onLeftRequest(null);
        if (categoryRightAdapter == null) {
            categoryRightAdapter = new ShopCategoryRightAdapter(act, this, listRightBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(categoryRightAdapter);
        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRightRequest(classifyId);
            }
        });
        categoryRightAdapter.setOnClickListener((position, classifyId, classifyTitle) -> {
            if (this.classifyId == classifyId)return;
            this.classifyId = classifyId;
            mB.tvText.setText(categoryName + " " + classifyTitle);
            categoryRightAdapter.setmPosition(position);
            categoryRightAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                EventBus.getDefault().post(new BusinessCassificationInEvent(classifyId, mB.tvText.getText().toString()));
                pop();
                break;
        }
    }

    @Override
    public void setLeft(List<DataBean> result) {
        listLeftBean.clear();
        listLeftBean.addAll(result);
        categoryLeftAdapter.setmPosition(0);
        classifyId = listLeftBean.get(0).getClassifyId();
        categoryName = listLeftBean.get(0).getClassifyTitle();
        mB.tvText.setText(categoryName);
        mB.refreshLayout.startRefresh();
        categoryLeftAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRight(List<DataBean> result) {
        listRightBean.clear();
        listRightBean.addAll(result);
        categoryRightAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

}
