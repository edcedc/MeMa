package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.utils.cache.ShareIsLoginCache;
import com.yc.mema.view.LoginFrg;
import com.yc.mema.view.OrderListFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 21:31
 */
public class OrderListAct extends BaseActivity {

    private int position;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {
        position = bundle.getInt("position");
    }

    @Override
    protected void initView() {
        OrderListFrg frg = OrderListFrg.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        frg.setArguments(bundle);
        if (findFragment(OrderListFrg.class) == null) {
            loadRootFragment(R.id.fl_container, frg);
        }
    }
}
