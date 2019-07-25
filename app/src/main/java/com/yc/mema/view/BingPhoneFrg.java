package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FBingPhoneBinding;
import com.yc.mema.impl.BingPhoneContract;
import com.yc.mema.presenter.BingPhonePresenter;
import com.yc.mema.utils.CountDownTimerUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 17:01
 *  绑定手机号
 */
public class BingPhoneFrg extends BaseFragment<BingPhonePresenter, FBingPhoneBinding> implements BingPhoneContract.View, View.OnClickListener {
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_bing_phone;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.bind_phone));
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
                mPresenter.login(mB.etPhone.getText().toString(), mB.etCode.getText().toString());
                break;
        }
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }
}
