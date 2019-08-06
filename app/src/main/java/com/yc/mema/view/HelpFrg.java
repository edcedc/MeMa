package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FHelpBinding;
import com.yc.mema.impl.HelpContract;
import com.yc.mema.presenter.HelpPresenter;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 19:42
 *  帮助与反馈
 */
public class HelpFrg extends BaseFragment<HelpPresenter, FHelpBinding> implements HelpContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_help;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.help));
        mB.btSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                mPresenter.onSubmit(mB.etText.getText().toString(), mB.etPhone.getText().toString());
                break;
        }
    }

    @Override
    public void onFeed() {
        pop();
    }
}
