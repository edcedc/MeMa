package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/26
 * Time: 15:36
 */
public interface BirthdayRecordsContract {

    interface View extends IBaseView {

        void setData(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumber);

    }


}
