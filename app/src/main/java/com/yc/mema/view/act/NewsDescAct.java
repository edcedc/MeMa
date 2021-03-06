package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.NewsDescFrg;
import com.yc.mema.view.SetFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 15:41
 */
public class NewsDescAct extends BaseActivity {

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
        if (findFragment(NewsDescFrg.class) == null) {
            NewsDescFrg frg = NewsDescFrg.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getStringExtra("id"));
            bundle.putString("bean", getIntent().getStringExtra("bean"));
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }

}
