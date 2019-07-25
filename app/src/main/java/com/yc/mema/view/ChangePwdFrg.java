package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FChangePwdBinding;
import com.yc.mema.impl.ChangePwdContract;
import com.yc.mema.presenter.ChangePwdPresenter;
import com.yc.mema.utils.CountDownTimerUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 17:22
 *  修改密码
 */
public class ChangePwdFrg extends BaseFragment<ChangePwdPresenter, FChangePwdBinding> implements ChangePwdContract.View, View.OnClickListener {
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_change_pwd;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.change_password1));
        mB.tvCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code:
                mPresenter.code(mB.tvPhone.getText().toString());
                break;
            case R.id.bt_submit:
                mPresenter.login(mB.tvPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString());
                break;
        }
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }
}
