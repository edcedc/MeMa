package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 17:11
 */
public interface EditAddressContract {

    interface View extends IBaseView {

        void setEdit();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onAdd(String id, String name, String phone, String location, String desc, String parentId);
    }

}
