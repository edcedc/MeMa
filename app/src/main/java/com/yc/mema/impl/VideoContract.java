package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/30
 * Time: 15:31
 */
public interface VideoContract {

    interface View extends IBaseListView {

        void setComment(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumer);

        public abstract void onComment(int pagetNumer);
    }

}
