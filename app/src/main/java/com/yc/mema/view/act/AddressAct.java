package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.AddressFrg;
import com.yc.mema.view.LoginFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 22:44
 */
public class AddressAct extends BaseActivity {

    private int type;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
    }

    @Override
    protected void initView() {
        AddressFrg frg = AddressFrg.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        frg.setArguments(bundle);
        if (findFragment(AddressFrg.class) == null) {
            loadRootFragment(R.id.fl_container, frg);
        }
    }
}
