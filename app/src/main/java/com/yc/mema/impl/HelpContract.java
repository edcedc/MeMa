package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 19:48
 */
public interface HelpContract {

    interface View extends IBaseView {
        void onFeed();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onSubmit(String text, String phone);

    }

}
