package com.yc.mema.presenter;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.BirthdayRecordsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/26
 * Time: 15:36
 */
public class BirthdayRecordsPresenter extends BirthdayRecordsContract.Presenter{
    @Override
    public void onRequest(int pagetNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i<5;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
        mView.hideLoading();
    }
}
