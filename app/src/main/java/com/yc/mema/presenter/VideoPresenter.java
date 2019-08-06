package com.yc.mema.presenter;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.VideoContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/30
 * Time: 15:31
 */
public class VideoPresenter extends VideoContract.Presenter{

    @Override
    public void onRequest(int pagetNumer) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i<5;i++){
            list.add(new DataBean());
        }
        mView.hideLoading();
        mView.setData(list);
    }

    @Override
    public void onComment(int pagetNumer) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i<5;i++){
            list.add(new DataBean());
        }
        mView.setComment(list);
    }

}
