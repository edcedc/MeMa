package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseListView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 20:11
 */
public interface InformationContract {

    interface View extends IBaseListView {

        void onSaveUser();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void submit(String head, String name, String time);

        public abstract void head(String head);

        public abstract void name(String name);

        public abstract void mema(String time, String mema, int updataMema);

        public abstract void birthday(String birthday);

        public abstract void sex(int type);

        public abstract void onRequest(String parentId);


        public abstract void address(String parentId);
    }

}
