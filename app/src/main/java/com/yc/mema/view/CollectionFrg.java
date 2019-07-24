package com.yc.mema.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yc.mema.R;
import com.yc.mema.adapter.MyPagerAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.databinding.FCollectBinding;
import com.yc.mema.event.CollectionInEvent;
import com.yc.mema.view.act.HtmlAct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 15:52
 *  我的收藏
 */
public class CollectionFrg extends BaseFragment<BasePresenter, FCollectBinding> {

    private TextView topRight;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_collect;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.collection), getString(R.string.manage));
        EventBus.getDefault().register(this);
        topRight = view.findViewById(R.id.top_right);
        ArrayList<Fragment> mFragments = new ArrayList<>();
        String[] strings = new String[3];
        strings[0] = getString(R.string.birthday_prone);
        strings[1] = getString(R.string.consult);
        strings[2] = getString(R.string.gift);
        for (int i = 0; i < strings.length; i++){
            if (i == 0){
                CollectionChildFrg frg = new CollectionChildFrg();
                Bundle bundle = new Bundle();
                bundle.putInt("type", i);
                frg.setArguments(bundle);
                mFragments.add(frg);
            }else {
                CollectionChildFrg frg = new CollectionChildFrg();
                Bundle bundle = new Bundle();
                bundle.putInt("type", i);
                frg.setArguments(bundle);
                mFragments.add(frg);
            }
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
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        if (topRight.getText().toString().equals(getString(R.string.manage))){
            setTitle(getString(R.string.collection), getString(R.string.cancel));
            EventBus.getDefault().post(new CollectionInEvent(true));
        }else {
            setTitle(getString(R.string.collection), getString(R.string.manage));
            EventBus.getDefault().post(new CollectionInEvent(false));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void CollectionInEvent(CollectionInEvent event){
        if (event.isReduction){
            setTitle(getString(R.string.collection), getString(R.string.manage));
        }
    }

}
