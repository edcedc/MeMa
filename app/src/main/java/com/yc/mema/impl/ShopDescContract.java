package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/28
 * Time: 16:01
 */
public interface ShopDescContract {

    interface View extends IBaseListView {

        void setData(DataBean bean);

        void setCollState(int finalType);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String id);

        public abstract void onColl(String id, int isTrue);

        public abstract void onComment(int pagerNumber, String id);
    }

}
