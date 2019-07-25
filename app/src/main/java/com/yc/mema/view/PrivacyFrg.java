package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FPrivacyBinding;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 17:57
 *  隐私
 */
public class PrivacyFrg extends BaseFragment<BasePresenter, FPrivacyBinding> implements View.OnClickListener {

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_privacy;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.privacy));
        mB.tvBlacklist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_blacklist:
                UIHelper.startBlackListFrg(this);
                break;
        }
    }
}
