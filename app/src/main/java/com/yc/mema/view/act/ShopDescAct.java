package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.SetFrg;
import com.yc.mema.view.ShopDescFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/28
 * Time: 11:50
 */
public class ShopDescAct extends BaseActivity {

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
        setSofia(false);
        ShopDescFrg frg = ShopDescFrg.newInstance();
        if (findFragment(ShopDescFrg.class) == null) {
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }

}
