package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.LoginFrg;
import com.yc.mema.view.OrderDescFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 18:56
 */
public class OrderDescAct extends BaseActivity {

    private String id;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected void initView() {
        OrderDescFrg frg = OrderDescFrg.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        frg.setArguments(bundle);
        if (findFragment(OrderDescFrg.class) == null) {
            loadRootFragment(R.id.fl_container, frg);
        }
    }
}
