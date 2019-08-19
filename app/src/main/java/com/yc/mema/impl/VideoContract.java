package com.yc.mema.impl;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

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

        void setVideoZan(int position, int finalType, AppCompatImageView iv_zan, AppCompatTextView tv_zan);

        void setVideoColl(int position, int finalType, AppCompatImageView iv_coll, AppCompatTextView tv_coll);

        void setSecondComment(int position, DataBean result);

        void setDelVideo();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(int pagetNumer, int type);

        public abstract void onComment(String videoId, int pagetNumer);

        public abstract void onZan(int position, String discussId, int type);

        public abstract void onFirstComment(String videoId, String text);

        public abstract void onVideoZan(String id, int type, int position, AppCompatImageView iv_zan, AppCompatTextView tv_zan);

        public abstract void onVideoColl(String id, int type, int position, AppCompatImageView iv_coll, AppCompatTextView tv_coll);

        public abstract void onSecondComment(int position, String videoId, String discussId, String text, String pUserId);

        public abstract void onDelVideo(String videoId);
    }

}
