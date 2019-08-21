package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/9
 * Time: 16:14
 */
public interface MemorandumContract {

    interface View extends IBaseView {
        void setData(List<DataBean> list);

        void setDelBr(int position);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRequest(String nowDay);

        public abstract void onDelBr(int i, String bookId);
    }

}
