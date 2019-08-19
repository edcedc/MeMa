package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/19
 * Time: 15:43
 */
public interface ApplyContract {

    interface View extends IBaseView {

        void setRole(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRole();

        public abstract void onSaveAgent(String name, String phone, String roleId, String county, String text);
    }

}
