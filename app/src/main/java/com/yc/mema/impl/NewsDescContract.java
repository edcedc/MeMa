package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 23:51
 */
public interface NewsDescContract{

    interface View extends IBaseListView {

        void setInformation(DataBean result);

        void firstComment(DataBean result);

        void secondComment(int position, DataBean result);

        void onZan(int position, int type);

        void setInfoZan(int finalIsTrue);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String id, int pagerNumper, int type);

        public abstract void onInformation(String id);

        public abstract void onFirstComment(String id, String text);

        public abstract void onSecondComment(int position, String infoId, String discussId, String text, String pUserId);

        public abstract void onZan(int position, String discussId, int type);

        public abstract void onInfoPraise(String id, int isTrue);
    }

}
