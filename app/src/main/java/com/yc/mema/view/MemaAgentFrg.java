package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.databinding.FMemaAgentBinding;
import com.yc.mema.utils.PopupWindowTool;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/9
 * Time: 15:00
 */
public class MemaAgentFrg extends BaseFragment<BasePresenter, FMemaAgentBinding> {

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_mema_agent;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.mema_agent));
        JSONObject userObj = User.getInstance().getUserObj();
        String inviteCode = userObj.optString("inviteCode");
        mB.tvCode.setText(inviteCode);


        mB.btSubmit.setOnClickListener(view1 -> {
            PopupWindowTool.showCode(act);
        });
    }
}
