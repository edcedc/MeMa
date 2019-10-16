package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 15:58
 */
public interface ImmediatelyContract {

    interface View extends IBaseView {

        void setAddress(DataBean bean);

        void setOrder(String orderId);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onAddress();

        public abstract void onOrder(String id, String addressId, String sku, int skuNum, double allPrice, String orderId, String businessId);

        public abstract void onWxPay(String orderId, int po);
    }

}
