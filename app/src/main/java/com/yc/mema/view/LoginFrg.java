package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gyf.immersionbar.ImmersionBar;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FLoginBinding;
import com.yc.mema.impl.LoginContract;
import com.yc.mema.presenter.LoginPresenter;
import com.yc.mema.utils.CountDownTimerUtils;
import com.yc.mema.utils.cache.SharedAccount;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.weight.TabEntity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 16:32
 *  登录
 */
public class LoginFrg extends BaseFragment<LoginPresenter, FLoginBinding> implements LoginContract.View, View.OnClickListener {

    public static LoginFrg newInstance() {
        Bundle args = new Bundle();
        LoginFrg fragment = new LoginFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private int mPosition;
    
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_login;
    }

    @Override
    protected void initView(View view) {
        ImmersionBar.with(this).navigationBarColor(R.color.white).statusBarDarkFont(true).init();
        mB.tvCode.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mB.tvAgreement.setOnClickListener(this);
        mB.tvAgreement1.setOnClickListener(this);
        mB.tvForget.setOnClickListener(this);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#EE3257"));
        SpannableString hText = new SpannableString("注册或登录即代表你同意" + "《用户协议》");
        hText.setSpan(colorSpan, 0, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvAgreement.setText(hText);

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        String[] strings = {getString(R.string.login), getString(R.string.register)};
        for (String s : strings){
            mTabEntities.add(new TabEntity(s));
        }
        mB.tbLayout.setTabData(mTabEntities);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mPosition = position;
                if (position == 0){
                    mB.btSubmit.setText(getString(R.string.login));
                    mB.gpRe.setVisibility(View.GONE);
                    mB.gpLogin.setVisibility(View.VISIBLE);
                }else {
                    mB.btSubmit.setText(getString(R.string.register));
                    mB.gpRe.setVisibility(View.VISIBLE);
                    mB.gpLogin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                mPresenter.login(mB.etPhone.getText().toString(), mB.etCode.getText().toString(), mB.etPwd.getText().toString(), mB.cbSubmit.isChecked(), mPosition);
                break;
            case R.id.tv_code:
                mPresenter.code(mB.etPhone.getText().toString());
                break;
            case R.id.tv_forget:
                UIHelper.startForgetFrg(this);
                break;
            case R.id.tv_agreement:
            case R.id.tv_agreement1:
                UIHelper.startHtmlAct(HtmlAct.REGISTER);
                break;
        }
    }

    @Override
    public void onCode() {
        new CountDownTimerUtils(act, 60000, 1000, mB.tvCode).start();
    }

    @Override
    public void onResgist(String phone, String pwd) {
        mB.tbLayout.setCurrentTab(0);
        mPosition = 0;
        mB.btSubmit.setText(getString(R.string.login));
        mB.gpRe.setVisibility(View.GONE);
        mB.gpLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLogin(JSONObject user) {
        if (user.optString("headUrl").equals("null") || user.optString("nickName").equals("null") || user.optString("birthday").equals("null")){
            UIHelper.startInformationFrg(this);
        }else {
            act.finish();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        SharedAccount account = SharedAccount.getInstance(act);
        String mobile = account.getMobile();
        String pwd = account.getPwd();
        if (!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(pwd)){
            mB.etPhone.setText(mobile);
            mB.etPwd.setText(pwd);
        }
    }
}
