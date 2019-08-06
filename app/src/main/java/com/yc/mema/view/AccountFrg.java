package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FAccountBinding;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 16:26
 *  账号与安全
 */
public class AccountFrg extends BaseFragment<BasePresenter, FAccountBinding> implements View.OnClickListener {

    @Override
    public void initPresenter() {
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        setData(User.getInstance().getUserObj());
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_account;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.account));
        mB.lyPhone.setOnClickListener(this);
        mB.tvPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ly_phone:
                UIHelper.startBingPhoneFrg(this);
                break;
            case R.id.tv_pwd:
                UIHelper.startChangePwdFrg(this);
                break;
        }
    }
}
