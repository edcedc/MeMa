package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/31
 * Time: 16:55
 */
public interface ReleaseContract {

    interface View extends IBaseView {
    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void onRelease(String text, String path);
    }

}