package com.yc.mema.presenter;

import android.os.Handler;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.ReportNewsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 22:26
 */
public class ReportNewsPresenter extends ReportNewsContract.Presenter{
    @Override
    public void onRequest(int pagerNumber) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<DataBean> list = new ArrayList<>();
                for (int i = 0;i<15;i++){
                    list.add(new DataBean());
                }
                mView.setData(list);
                mView.hideLoading();
            }
        }, 500);
    }

    @Override
    public void onReport() {

    }
}
