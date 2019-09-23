package com.yc.mema.impl;

import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;
import com.yc.mema.weight.WithScrollGridView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/11
 * Time: 18:07
 */
public interface SixContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);

        void setTea(List<DataBean> list);

        void setCake(List<DataBean> list);

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onBanner();

        public abstract void onLabel();

        public abstract void onTea();

        public abstract void onCake();

        public abstract void onRequest(int pagerNumber);
    }

}
