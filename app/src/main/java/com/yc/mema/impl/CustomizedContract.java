package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/17
 * Time: 18:49
 */
public interface CustomizedContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);

        void setDesc(List<DataBean> list);

        void setHot(List<DataBean> list);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onDesc();

        public abstract void onHot();

        public abstract void onRequest(int pagerNumber);

    }

}
