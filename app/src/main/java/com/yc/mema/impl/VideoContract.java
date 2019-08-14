package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/30
 * Time: 15:31
 */
public interface VideoContract {

    interface View extends IBaseListView {

        void setComment(List<DataBean> list);

        void onZan(int position, int finalType);

        void setFirstComment(DataBean result);

        void setVideoZan(int position, int finalType);

        void setVideoColl(int position, int finalType);

        void setSecondComment(int position, DataBean result);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumer, int type);

        public abstract void onComment(String videoId, int pagetNumer);

        public abstract void onZan(int position, String discussId, int type);

        public abstract void onFirstComment(String videoId, String text);

        public abstract void onVideoZan(String id, int type, int position);

        public abstract void onVideoColl(String id, int i, int position);

        public abstract void onSecondComment(int position, String videoId, String discussId, String text, String pUserId);
    }

}
