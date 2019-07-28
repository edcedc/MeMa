package com.yc.mema.presenter;

import android.support.v7.widget.RecyclerView;

import com.yc.mema.R;
import com.yc.mema.adapter.LabelAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.OneContract;
import com.yc.mema.weight.WithScrollGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 16:58
 */
public class OnePresenter extends OneContract.Presenter {
    @Override
    public void onRequest(int pagetNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new DataBean());
        }
        mView.setData(list);
        mView.hideLoading();
    }

    @Override
    public void onBanner() {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DataBean bean = new DataBean();
            bean.setImage("http://ws4.sinaimg.cn/mw600/b3bf794dly1g5d7zebimwj21j415chdt.jpg");
            list.add(bean);
        }
        mView.setBanner(list);
    }

    @Override
    public void onGridView(BaseFragment root, WithScrollGridView recyclerView) {
        String[] str = {act.getString(R.string.all), act.getString(R.string.cake), act.getString(R.string.eat), act.getString(R.string.drink),
                act.getString(R.string.play), act.getString(R.string.hHappy), act.getString(R.string.gift), act.getString(R.string.free)};
        int[] img = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,};

        List<DataBean> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            DataBean bean = new DataBean();
            bean.setImg(img[i]);
            bean.setTitle(str[i]);
            list.add(bean);
        }
        LabelAdapter adapter = new LabelAdapter(act, list);
        recyclerView.setAdapter(adapter);

    }
}
