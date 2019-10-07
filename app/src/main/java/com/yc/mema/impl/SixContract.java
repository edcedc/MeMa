package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

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

        void setHomeClassify(List<DataBean> list);

        void setLabel(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onBanner();

        public abstract void onLabel();

        public abstract void onGetHomeClassify(String ids);

        public abstract void onRequest(int pagerNumber, int low, int up, int type, String county);
    }

}
