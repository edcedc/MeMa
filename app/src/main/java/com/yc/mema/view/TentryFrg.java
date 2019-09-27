package com.yc.mema.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.databinding.FTentryBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.event.BusinessCassificationInEvent;
import com.yc.mema.event.LocationInEvent;
import com.yc.mema.event.TentryInEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 15:39
 */
public class TentryFrg extends BaseFragment<BasePresenter, FTentryBinding> implements View.OnClickListener {

    private final int FIRST = 0;
    private final int SECOND = 1;
    private final int THIRD = 2;
    private final int FOUR = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_tentry;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.merchant_entry), getString(R.string.help1));
        mB.tvInfo.setOnClickListener(this);
        mB.tvAudit.setOnClickListener(this);
        mB.tvQualifications.setOnClickListener(this);
//        setType(0);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(TentryChildFrg.class);
        if (firstFragment == null) {
            mFragments[FIRST] = TentryChildFrg.newInstance();
            mFragments[SECOND] = TentryChildOneFrg.newInstance();
            mFragments[THIRD] = TentryChildTwoFrg.newInstance();
            mFragments[FOUR] = TentryChildThreeFrg.newInstance();

            loadMultipleRootFragment(R.id.fl_container,
                    FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOUR]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = TentryChildOneFrg.newInstance();
            mFragments[THIRD] = TentryChildTwoFrg.newInstance();
            mFragments[FOUR] = TentryChildThreeFrg.newInstance();
        }
        setSwipeBackEnable(false);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
    }

    @Override
    public void onClick(View view) {

    }

    private void setType(int i){
        switch (i){
            case 0:
                mB.tvInfo.setTextColor(act.getResources().getColor(R.color.red_EC5B44));
                mB.tvInfo.setBackgroundResource(R.mipmap.bg_progressbar);
                mB.tvQualifications.setTextColor(act.getResources().getColor(R.color.tab_gray));
                mB.tvAudit.setTextColor(act.getResources().getColor(R.color.tab_gray));
                mB.tvQualifications.setBackgroundResource(0);
                mB.tvAudit.setBackgroundResource(0);
                mB.tvQualifications.setBackgroundColor(0);
                mB.tvAudit.setBackgroundColor(0);
                break;
            case 1:
                mB.tvQualifications.setTextColor(act.getResources().getColor(R.color.red_EC5B44));
                mB.tvQualifications.setBackgroundResource(R.mipmap.bg_progressbar);
                mB.tvInfo.setBackgroundColor(act.getResources().getColor(R.color.yellow_FFF5DC));
                mB.tvInfo.setTextColor(act.getResources().getColor(R.color.tab_gray));
                mB.tvAudit.setTextColor(act.getResources().getColor(R.color.tab_gray));
                mB.tvAudit.setBackgroundResource(0);
                mB.tvAudit.setBackgroundColor(0);
                break;
            case 2:
                mB.tvAudit.setTextColor(act.getResources().getColor(R.color.red_EC5B44));
                mB.tvAudit.setBackgroundResource(R.mipmap.bg_progressbar);
                mB.tvQualifications.setBackgroundColor(act.getResources().getColor(R.color.yellow_FFF5DC));
                mB.tvInfo.setBackgroundColor(act.getResources().getColor(R.color.yellow_FFF5DC));
                mB.tvInfo.setTextColor(act.getResources().getColor(R.color.tab_gray));
                mB.tvQualifications.setTextColor(act.getResources().getColor(R.color.tab_gray));
                break;
        }
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Subscribe
    public void onMainTentryInEvent(TentryInEvent event){
        mB.layout.setVisibility(View.VISIBLE);
        showHideFragment(mFragments[event.position], mFragments[event.prePosition]);
        switch (event.position){
            case 1:
                setType(0);
                break;
            case 2:
                setType(1);
                break;
            case 3:
                setType(2);
                break;
        }
    }


}
