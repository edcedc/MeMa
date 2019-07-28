package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 21:00
 */
public interface GiftDescContract {

    interface View extends IBaseView {

        void setData(DataBean bean);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest();

    }

}
