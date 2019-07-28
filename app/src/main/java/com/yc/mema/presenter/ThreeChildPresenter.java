package com.yc.mema.presenter;

import android.os.Handler;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.ThreeChildContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 21:34
 */
public class ThreeChildPresenter extends ThreeChildContract.Presenter {
    @Override
    public void onRequest(String id, int pagetNumber) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<10;i++){
                    list.add(new DataBean());
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 500);
    }

    @Override
    public void onBanner() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    DataBean bean = new DataBean();
                    bean.setImage("http://ws4.sinaimg.cn/mw600/b3bf794dly1g5d7zebimwj21j415chdt.jpg");
                    list.add(bean);
                }
                mView.setBanner(list);
            }
        }, 500);
    }
}
