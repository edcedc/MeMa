package com.yc.mema.presenter;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.GiftDescContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 21:00
 */
public class GiftDescPresenter extends GiftDescContract.Presenter {
    @Override
    public void onRequest() {
        mView.setData(new DataBean());
        mView.hideLoading();
    }
}
