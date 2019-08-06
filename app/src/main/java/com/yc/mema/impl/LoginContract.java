package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 17:14
 */
public interface LoginContract {

    interface View extends IBaseView {

        void onCode();

        void onResgist(String phone, String pwd);

        void onLogin(JSONObject user);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void code(String phone);

        public abstract void login(String phone, String code, String pwd, boolean checked, int mPosition);

    }

}
