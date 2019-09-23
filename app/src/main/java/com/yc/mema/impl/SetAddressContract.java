package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 16:32
 */
public interface SetAddressContract {

    interface View extends IBaseListView {
        void setMoRen(int position);

        void setDel(int position);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagerNumber);

        public abstract void onMoRen(String id, int position);

        public abstract void onDel(String id, int position);

        public abstract void onEdit(String id, int position);
    }

}
