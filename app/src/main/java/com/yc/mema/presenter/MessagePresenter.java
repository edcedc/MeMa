package com.yc.mema.presenter;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.MessageContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/31
 * Time: 18:39
 */
public class MessagePresenter extends MessageContract.Presenter{

    @Override
    public void onRequest(int pagerNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i<5;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
        mView.hideLoading();
    }

    @Override
    public void onSystem(int pagerNumber) {

    }
}
