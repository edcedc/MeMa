package com.yc.mema.view.act;

import android.content.Intent;
import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.view.SetFrg;
import com.yc.mema.view.UserInfoFrg;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 15:41
 */
public class SetAct extends BaseActivity {

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
        if (findFragment(SetFrg.class) == null) {
            loadRootFragment(R.id.fl_container, SetFrg.newInstance());
        }
    }

}
