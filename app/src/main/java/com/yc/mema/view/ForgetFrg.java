package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FForgetBinding;
import com.yc.mema.impl.ForgetContract;
import com.yc.mema.presenter.ForgetPresenter;
import com.yc.mema.utils.CountDownTimerUtils;


/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/16
 * Time: 20:13
 *  找回密码
 */
public class ForgetFrg extends BaseFragment<ForgetPresenter, FForgetBinding> implements ForgetContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_forget;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.reset_password));
        mB.btSubmit.setOnClickListener(this);
        mB.tvCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString());
                break;
            case R.id.bt_submit:
                mPresenter.forget(mB.etPhone.getText().toString(), mB.etPwd.getText().toString(), mB.etCode.getText().toString());
                break;
        }
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }
}
