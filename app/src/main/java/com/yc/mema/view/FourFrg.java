package com.yc.mema.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.mema.R;
import com.yc.mema.adapter.MyPagerAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseListContract;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FFourBinding;
import com.yc.mema.weight.TabEntity;

import java.util.ArrayList;

/**
 * Created by wb  yyc
 * User: 501807647@qq.com
 * Date: 2019/6/16
 * Time: 1:14
 */
public class FourFrg extends BaseFragment<BasePresenter, FFourBinding> implements View.OnClickListener {

    public static FourFrg newInstance() {
        Bundle args = new Bundle();
        FourFrg fragment = new FourFrg();
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
        return R.layout.f_four;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        mB.tvWorld.setOnClickListener(this);
        mB.tvFriend.setOnClickListener(this);
        mB.ivCamera.setOnClickListener(this);
        ArrayList<Fragment> mFragments = new ArrayList<>();
        mFragments.add(VideoFrg.newInstance());
        mFragments.add(GoodFriendFrg.newInstance());
        String[] str = {"", ""};
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments, str));
//        mB.viewPager.setOffscreenPageLimit(1);
        mB.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0){
                    mB.ivCamera.setBackgroundResource(R.mipmap.y40);
                    mB.tvWorld.setAlpha((float) 0.8);
                    mB.tvFriend.setAlpha((float) 0.8);
                }else {
                    mB.ivCamera.setBackgroundResource(R.mipmap.y42);
                    mB.tvWorld.setAlpha(1);
                    mB.tvFriend.setAlpha(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_world:
                mB.viewPager.setCurrentItem(0);
                break;
            case R.id.tv_friend:
                mB.viewPager.setCurrentItem(1);
                break;
            case R.id.iv_camera:
                UIHelper.startReleaseAct();
                break;
        }
    }
}
