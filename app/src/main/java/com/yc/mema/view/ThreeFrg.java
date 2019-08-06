package com.yc.mema.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.mema.R;
import com.yc.mema.adapter.MyPagerAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseListContract;
import com.yc.mema.base.BaseListPresenter;
import com.yc.mema.base.User;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FOneBinding;
import com.yc.mema.databinding.FThreeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:11
 */
public class ThreeFrg extends BaseFragment<BaseListPresenter, FThreeBinding> implements BaseListContract.View, View.OnClickListener{

    public static ThreeFrg newInstance() {
        Bundle args = new Bundle();
        ThreeFrg fragment = new ThreeFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
            isRequest = false;
            showLoadDataing();
            mPresenter.onRequest(CloudApi.informationGetInfoSortList);
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_three;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        view.findViewById(R.id.et_search).setOnClickListener(this);
        RoundLinearLayout lySearch = view.findViewById(R.id.ly_search);
        lySearch.getDelegate().setBackgroundColor(act.getColor(R.color.white_f4f4f4));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.et_search:
            UIHelper.startSearchNewsFrg(this);
            break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        ArrayList<Fragment> mFragments = new ArrayList<>();
        String[] strings = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            DataBean bean = list.get(i);
            strings[i] = bean.getSortName();
            ThreeChildFrg frg = new ThreeChildFrg();
            Bundle bundle = new Bundle();
            bundle.putString("id", bean.getSortId());
            frg.setArguments(bundle);
            mFragments.add(frg);
        }
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments, strings));
        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(list.size() - 1);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mB.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }
}
