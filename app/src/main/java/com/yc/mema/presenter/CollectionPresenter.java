package com.yc.mema.presenter;

import android.os.Handler;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.CollectionContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 16:18
 */
public class CollectionPresenter extends CollectionContract.Presenter{
    @Override
    public void onRequest(int type, int pagetNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i<3;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
        mView.hideLoading();
    }
}
