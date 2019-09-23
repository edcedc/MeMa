package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/12
 * Time: 14:55
 */
public interface OrderDescContract {

    interface View extends IBaseView {
        void setData(DataBean result);

        void setRefresh();
    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void onRequest(String id);

        public abstract void onWxPay(String orderId, int position);

        public abstract void onUpdateOrder(String orderId, int type);
    }

}
