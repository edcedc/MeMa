package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.CustomizedDescFrg;
import com.yc.mema.view.GiftDescFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/17
 * Time: 17:44
 *  商家详情
 */
public class CustomizedDescAct extends BaseActivity {

    private String bean;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = bundle.getString("bean");
    }

    @Override
    protected void initView() {
        if (findFragment(CustomizedDescFrg.class) == null) {
            CustomizedDescFrg frg = CustomizedDescFrg.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("bean", bean);
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }

}
