package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSetBinding;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.utils.cache.ShareSessionIdCache;
import com.yc.mema.utils.cache.SharedAccount;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 15:41
 *  设置
 */
public class SetFrg extends BaseFragment<BasePresenter, FSetBinding> implements View.OnClickListener {

    public static SetFrg newInstance() {

        Bundle args = new Bundle();

        SetFrg fragment = new SetFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_set;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set));
        mB.tvAccount.setOnClickListener(this);
        mB.tvMessage.setOnClickListener(this);
        mB.tvPrivacy.setOnClickListener(this);
        mB.tvClear.setOnClickListener(this);
        mB.tvHelp.setOnClickListener(this);
        mB.tvAbout.setOnClickListener(this);
        mB.btSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_account:
                if (!((BaseActivity)act).isLogin())return;
                UIHelper.startAccountFrg(this);
                break;
            case R.id.tv_message:
                UIHelper.startMsgFrg(this);
                break;
            case R.id.tv_privacy:
                UIHelper.startPrivacyFrg(this);
                break;
            case R.id.tv_clear:
//                PopupWindowTool.showDialog(act, PopupWindowTool.clear, () -> showToast("清除成功"));
                showToast("清除成功");
                break;
            case R.id.tv_help:
                UIHelper.startHelpFrg(this);
                break;
            case R.id.tv_about:
                UIHelper.startAboutFrg(this);
                break;
            case R.id.bt_submit:
                UIHelper.startLoginAct();
//                SharedAccount.getInstance(act).remove();
                ShareSessionIdCache.getInstance(act).remove();
                User.getInstance().setLogin(false);
                User.getInstance().setUserObj(null);
                ActivityUtils.finishAllActivities();
                break;
        }
    }
}
