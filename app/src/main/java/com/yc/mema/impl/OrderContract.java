package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 21:48
 */
public interface OrderContract {

    interface View extends IBaseListView {

        void setRefresh(int position);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber, int type);

        public abstract void onUpdateOrder(int position, String orderId, int type);

    }

}
