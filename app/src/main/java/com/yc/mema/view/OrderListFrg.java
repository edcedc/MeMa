package com.yc.mema.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.mema.R;
import com.yc.mema.adapter.MyPagerAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.databinding.FOrderListBinding;
import com.yc.mema.utils.Constants;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 21:31
 */
public class OrderListFrg extends BaseFragment<BasePresenter, FOrderListBinding> {

    private int position;

    public static OrderListFrg newInstance() {
        
        Bundle args = new Bundle();
        
        OrderListFrg fragment = new OrderListFrg();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void initPresenter() {
        
    }

    @Override
    protected void initParms(Bundle bundle) {
        position = bundle.getInt("position");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_order_list;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.my_order));
        ArrayList<Fragment> mFragments = new ArrayList<>();
        String[] strings = new String[4];
        strings[0] = getString(R.string.pending_payment);
        strings[1] = getString(R.string.to_shipped);
        strings[2] = getString(R.string.goods_received);
        strings[3] = getString(R.string.completed);
        for (int i = 0; i < strings.length; i++){
            OrderChildFrg frg = new OrderChildFrg();
            Bundle bundle = new Bundle();
            switch (i){
                case 0:
                    bundle.putInt("type", Constants.waitPay);
                    break;
                case 1:
                    bundle.putInt("type", Constants.successPay);
                    break;
                case 2:
                    bundle.putInt("type", Constants.waitDeliver);
                    break;
                case 3:
                    bundle.putInt("type", Constants.successDeliver);
                    break;
            }
            frg.setArguments(bundle);
            mFragments.add(frg);
        }
        mB.viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), mFragments, strings));
        mB.tbLayout.setViewPager(mB.viewPager);
        mB.viewPager.setOffscreenPageLimit(strings.length - 1);
        mB.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mB.viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        mB.viewPager.setCurrentItem(position);
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }


}
