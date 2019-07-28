package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.GiftDescFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 15:41
 */
public class GiftAct extends BaseActivity {

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
        if (findFragment(GiftDescFrg.class) == null) {
            loadRootFragment(R.id.fl_container, GiftDescFrg.newInstance());
        }
    }

}
