package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 20:35
 */
public interface ShopCategoryContract {

    interface View extends IBaseView {

        void setLeft(List<DataBean> result);

        void setRight(List<DataBean> result);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onLeftRequest(String classifyId);

        public abstract void onRightRequest(String id);

    }

}
