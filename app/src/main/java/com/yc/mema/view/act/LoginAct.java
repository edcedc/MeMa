package com.yc.mema.view.act;

import android.content.Intent;
import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.utils.cache.ShareIsLoginCache;
import com.yc.mema.view.LoginFrg;
import com.yc.mema.view.SplashFrg;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 16:32
 */
public class LoginAct extends BaseActivity {
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
        ShareIsLoginCache.getInstance(act).save(true);
        if (findFragment(LoginFrg.class) == null) {
            loadRootFragment(R.id.fl_container, LoginFrg.newInstance());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            EventBus.getDefault().post(new CameraInEvent(requestCode, data));
        }
    }

}
