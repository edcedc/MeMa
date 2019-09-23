package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/11
 * Time: 22:53
 */
public interface SearchShopContract {

    interface View extends IBaseListView {
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumber, int type, String categoryId, int di, int gao);

    }


}
