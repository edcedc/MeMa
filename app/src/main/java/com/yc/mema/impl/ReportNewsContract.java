package com.yc.mema.impl;

import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 22:26
 */
public interface ReportNewsContract {

    interface View extends IBaseView {

        void setComplain(DataBean result);

        void setReport();

        void setData(List<DataBean> list);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String complainId);

        public abstract void onComplainList(int type);

        public abstract void onReport(String discussId, String soId, int type, String infoId);

        public abstract void onReport(String id, String soId, List<LocalMedia> localMediaList, String content);

        public abstract void onGiftReport(String id, String soId, List<LocalMedia> localMediaList, String content);
    }

}
