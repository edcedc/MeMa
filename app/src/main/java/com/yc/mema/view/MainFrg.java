package com.yc.mema.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.yc.mema.MainActivity;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.databinding.FMainBinding;
import com.yc.mema.weight.buttonBar.BottomBar;
import com.yc.mema.weight.buttonBar.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 作者：yc on 2018/7/25.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class MainFrg extends BaseFragment<BasePresenter, FMainBinding> implements IBaseView {

    public static MainFrg newInstance() {
        Bundle args = new Bundle();
        MainFrg fragment = new MainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private final int FIRST = 0;
    private final int SECOND = 1;
    private final int THIRD = 2;
    private final int FOUR = 3;
    private final int FIVE = 4;

    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_main;
    }

    @Override
    protected void initView(View view) {
       mB.bottomBar
                .addItem(new BottomBarTab(_mActivity, R.mipmap.home_normal, "礼"))
//                .addItem(new BottomBarTab(_mActivity, R.mipmap.dibudaohan_yuanliao_2,"商"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.news_normal,"今"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.video_normal,"趴"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.user_normal,"君"));
        mB.bottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
//                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });
        mB.bottomBar.setCurrentItem(0);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            SupportFragment firstFragment = findChildFragment(SixFrg.class);
            if (firstFragment == null) {
                mFragments[FIRST] = SixFrg.newInstance();
                mFragments[SECOND] = ThreeFrg.newInstance();
                mFragments[THIRD] = FourFrg.newInstance();
                mFragments[FOUR] = FiveFrg.newInstance();

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
                mFragments[SECOND] = findChildFragment(ThreeFrg.class);
                mFragments[THIRD] = findChildFragment(FourFrg.class);
                mFragments[FOUR] = findChildFragment(FiveFrg.class);
            }
            setSwipeBackEnable(false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        setSofia(false);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public boolean onBackPressedSupport() {
        exitBy2Click();// 调用双击退出函数
//        return super.onBackPressedSupport();
        return true;
    }

    private Boolean isExit = false;

    private void exitBy2Click() {
        Handler tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            showToast("再按一次退出程序");
            tExit = new Handler();
            tExit.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            return;
        } else {
//            Cockroach.uninstall();
            ActivityUtils.finishAllActivities();
            System.exit(0);
        }
    }

}
