package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/26
 * Time: 17:00
 */
public interface AddBirthdayRecordsContract {

    interface View extends IBaseView {

        void setAdd();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void add(String name, String hb);

    }

}
