package com.yc.mema.view.act;

import android.content.Intent;
import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.view.GiftDescFrg;

import org.greenrobot.eventbus.EventBus;

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
            GiftDescFrg frg = GiftDescFrg.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("id", getIntent().getStringExtra("id"));
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
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
