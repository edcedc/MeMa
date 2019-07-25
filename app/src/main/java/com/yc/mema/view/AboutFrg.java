package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.databinding.FAboutBinding;
import com.yc.mema.utils.GoToScoreUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 19:59
 *  关于我们
 */
public class AboutFrg extends BaseFragment<BasePresenter, FAboutBinding> implements View.OnClickListener {
    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_about;
    }

    @Override
    protected void initView(View view) {
        setTitle("");
        mB.tvUpdate.setOnClickListener(this);
        mB.tvComplaint.setOnClickListener(this);
        mB.tvScoring.setOnClickListener(this);

        mB.tvVerison.setText("Version " +
                AppUtils.getAppVersionName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_scoring:
            case R.id.tv_complaint:
            case R.id.tv_update:
                GoToScoreUtils.getInstallAppMarkets(act);
                break;
        }
    }
}
