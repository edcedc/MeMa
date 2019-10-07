package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.BusinessDescFrg;
import com.yc.mema.view.OrderDescFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 19:02
 */
public class BusinessDescAct extends BaseActivity {

    private String id;
    private String title;

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
        title = bundle.getString("title");
    }

    @Override
    protected void initView() {
        BusinessDescFrg frg = BusinessDescFrg.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("title", title);
        frg.setArguments(bundle);
        if (findFragment(BusinessDescFrg.class) == null) {
            loadRootFragment(R.id.fl_container, frg);
        }
    }
}
