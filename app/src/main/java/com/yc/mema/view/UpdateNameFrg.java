package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FNameBinding;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 20:57
 *  更新名字
 */
public class UpdateNameFrg extends BaseFragment<InformationPresenter, FNameBinding> implements InformationContract.View {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_name;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_name), getString(R.string.submit1));
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();

    }

}
