package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FTentryChildThreeBinding;
import com.yc.mema.databinding.FTentryChildTwoBinding;
import com.yc.mema.impl.TentryContract;
import com.yc.mema.presenter.TentryPresenter;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 20:22
 */
public class TentryChildThreeFrg extends BaseFragment<TentryPresenter, FTentryChildThreeBinding> implements TentryContract.View, View.OnClickListener {


    public static TentryChildThreeFrg newInstance() {

        Bundle args = new Bundle();

        TentryChildThreeFrg fragment = new TentryChildThreeFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_tentry_child_three;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void setData(String name, String phone, String userId, String num, String bankName, String bankPhone, String bankId, String address, String addressDesc, int type, String category, String shopArea, String shopScope) {

    }
}
