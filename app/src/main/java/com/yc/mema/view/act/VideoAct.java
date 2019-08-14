package com.yc.mema.view.act;

import android.os.Bundle;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.view.SetFrg;
import com.yc.mema.view.VideoFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 15:41
 */
public class VideoAct extends BaseActivity {


    private int isVideoType;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {
        isVideoType = bundle.getInt("isVideoType");
    }

    @Override
    protected void initView() {
        if (findFragment(VideoFrg.class) == null) {
            VideoFrg frg = VideoFrg.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("isVideoType", isVideoType);
            frg.setArguments(bundle);
            loadRootFragment(R.id.fl_container, frg);
        }
    }

}
