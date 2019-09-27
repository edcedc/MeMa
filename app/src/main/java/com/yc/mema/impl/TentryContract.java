package com.yc.mema.impl;

import android.widget.ListView;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 16:12
 */
public interface TentryContract {


    interface View extends IBaseView {


        void setData(String name, String phone, String userId, String num, String bankName, String bankPhone, String bankId, String address, String addressDesc, int type, String category, String shopArea, String shopScope);
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onSure(String name, String phone, String userId, String num, String bankName, String bankPhone, String bankId, String address, String addressDesc, int type, String category, String shopArea, String shopScope, boolean isShop, boolean isMerchants);

        public abstract void onList(ListView listView);

        public abstract void onTwoSure(String yingy, String scsf, String sfz, String sff, String weis, String ship);
    }

}
