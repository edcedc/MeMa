package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 19:14
 */
public interface BusinessDescContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);

        void setTea(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber, String id, int low, int up, int type);

        public abstract void onBanner();

        public abstract void onTea();
    }

}
