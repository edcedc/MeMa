package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FTentryChildThreeBinding;
import com.yc.mema.databinding.FTentryChildTwoBinding;
import com.yc.mema.event.TentryInEvent;
import com.yc.mema.impl.TentryContract;
import com.yc.mema.presenter.TentryPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 20:22
 */
public class TentryChildThreeFrg extends BaseFragment<TentryPresenter, FTentryChildThreeBinding> implements TentryContract.View, View.OnClickListener {


    private int type = 0;
    private String reason;

    public static TentryChildThreeFrg newInstance() {

        Bundle args = new Bundle();

        TentryChildThreeFrg fragment = new TentryChildThreeFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
        reason = bundle.getString("reason");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_tentry_child_three;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.btSubmit.setOnClickListener(this);
        switch (type){
            case 0:
                mB.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,
                        getResources().getDrawable(R.mipmap.shop_verify_processing, null), null, null);
                mB.tvTitle.setText(getText(R.string.audit_z));
                mB.tvContent.setText(getText(R.string.mema42));
                mB.btSubmit.setVisibility(View.GONE);
                break;
            case 1:
                mB.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,
                        getResources().getDrawable(R.mipmap.shop_verify_past, null), null, null);
                mB.tvTitle.setText(getText(R.string.audit_c));
                mB.tvContent.setText(getText(R.string.mema40));
                mB.btSubmit.setText(getText(R.string.mema39));
                mB.btSubmit.setVisibility(View.GONE);
                break;
            case 2:
                mB.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,
                        getResources().getDrawable(R.mipmap.shop_verify_fail, null), null, null);
                mB.tvTitle.setText(getText(R.string.audit_s));
                mB.tvContent.setText(getText(R.string.mema41));
                mB.tvText.setText("原因：" + "\n" + reason);
                mB.btSubmit.setText(getText(R.string.resubmission));
                mB.btSubmit.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                if (type == 1){

                }else {
                    EventBus.getDefault().post(new TentryInEvent(1, 0));
                }
                break;
        }
    }

    @Override
    public void setData(List<DataBean> list) {

    }
}
