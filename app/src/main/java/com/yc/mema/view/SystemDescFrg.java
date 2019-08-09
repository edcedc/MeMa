package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FSystemDescBinding;
import com.yc.mema.utils.TimeUtil;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/8
 * Time: 19:06
 *  系统通知详情
 */
public class SystemDescFrg extends BaseFragment<BasePresenter, FSystemDescBinding> {

    private DataBean bean;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_system_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.system_notification));
        mB.tvTitle.setText(bean.getTitle());
        mB.tvTime.setText(TimeUtil.getTimeFormatText(bean.getCreateTime()));
        mB.tvContent.setText(bean.getContext());
    }
}
