package com.yc.mema.impl;


import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/16
 * Time: 20:15
 */
public interface ForgetContract {

    interface View extends IBaseView {

        void onCode();

        void onForget();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void code(String phone);

        public abstract void forget(String phone, String pwd, String code);

    }

}
