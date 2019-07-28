package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 22:26
 */
public interface ReportNewsContract {

    interface View extends IBaseListView {

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);

        public abstract void onReport();

    }

}
