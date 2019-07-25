package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 17:41
 */
public interface ChangePwdContract {

    interface View extends IBaseView {

        void onCode();

    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void code(String phone);

        public abstract void login(String phone, String code, String pwd);

    }

}
