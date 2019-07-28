package com.yc.mema.impl;

import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;
import com.yc.mema.weight.WithScrollGridView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 21:34
 */
public interface ThreeChildContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String id, int pagetNumber);
        public abstract void onBanner();

    }

}
