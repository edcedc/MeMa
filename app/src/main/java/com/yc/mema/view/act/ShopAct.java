package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.SetFrg;
import com.yc.mema.view.TwoFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/11
 * Time: 22:36
 */
public class ShopAct extends BaseActivity {
    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        if (findFragment(TwoFrg.class) == null) {
            loadRootFragment(R.id.fl_container, TwoFrg.newInstance());
        }
    }

}
