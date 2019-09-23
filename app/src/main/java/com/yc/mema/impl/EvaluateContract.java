package com.yc.mema.impl;

import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/12
 * Time: 19:06
 */
public interface EvaluateContract {

    interface View extends IBaseView {

    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void onSubmit(String goodId, String orderId, String toString, List<LocalMedia> localMediaList, float rat);
    }

}
