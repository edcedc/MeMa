package com.yc.mema.impl;

import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;
import com.yc.mema.weight.WithScrollGridView;

import java.util.List;
import java.util.Objects;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 16:58
 */
public interface OneContract {

    interface View extends IBaseListView {

        void setBanner(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String county, String search, int itemize, int pagetNumber);
        public abstract void onBanner();
        public abstract void onGridView(BaseFragment root, WithScrollGridView recyclerView);
        public abstract void onLabel();
    }

}
