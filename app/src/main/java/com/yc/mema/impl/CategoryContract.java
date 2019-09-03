package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/27
 * Time: 16:19
 */
public interface CategoryContract {

    interface View extends IBaseListView {

        void setLabel(List<DataBean> listBean, String categoryId, String name);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String id, int pagetNumber);
    }


}
