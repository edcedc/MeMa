package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/26
 * Time: 17:55
 */
public interface TwoContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);

        void setLabel(List<DataBean> list);

        void setWeight(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumber, int type, String categoryId, int di, int gao, String like);
        public abstract void onBanner();
        public abstract void onLabel();

        public abstract void onWeight();
    }

}
