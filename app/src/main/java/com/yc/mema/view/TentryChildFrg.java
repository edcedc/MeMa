package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FTentryChildBinding;
import com.yc.mema.databinding.FTentryChildOneBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.event.BusinessCassificationInEvent;
import com.yc.mema.event.TentryInEvent;
import com.yc.mema.impl.TentryContract;
import com.yc.mema.presenter.TentryPresenter;
import com.yc.mema.view.act.HtmlAct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 15:39
 */
public class TentryChildFrg extends BaseFragment<TentryPresenter, FTentryChildBinding> implements TentryContract.View, View.OnClickListener {

    public static TentryChildFrg newInstance() {
        
        Bundle args = new Bundle();
        
        TentryChildFrg fragment = new TentryChildFrg();
        fragment.setArguments(args);
        return fragment;
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
        return R.layout.f_tentry_child;
    }

    @Override
    protected void initView(View view) {
        mB.tvAgreement.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
        mPresenter.onList(mB.listView);
        setSwipeBackEnable(false);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
        SpannableString hText = new SpannableString(getString(R.string.mema37));
        hText.setSpan(colorSpan, 9, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        hText.setSpan(new AbsoluteSizeSpan(15, true), 9, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mB.tvText.setText(hText);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_agreement:
                UIHelper.startHtmlAct(HtmlAct.TENTRY, null);
                break;
            case R.id.bt_submit:
                if (!mB.cb.isChecked()){
                    showToast(getString(R.string.error_1));
                    return;
                }
                EventBus.getDefault().post(new TentryInEvent(1, 0));
                break;
        }
    }

    @Override
    public void setData(List<DataBean> list) {
        
    }
}
