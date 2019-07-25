package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 17:49
 *  消息通知
 */
public class MsgFrg extends BaseFragment {
    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_msg;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.message_not));
    }
}
