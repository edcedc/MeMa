package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.view.FiveFrg;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 15:09
 */
public interface FiveContract {

    interface View extends IBaseView {
        void setData(JSONObject userObj);

        void setDateError();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onInfo();

        public abstract void onaUserAgent(FiveFrg fiveFrg);
    }

}
