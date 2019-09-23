package com.yc.mema.impl;

import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.IBaseView;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/19
 * Time: 15:43
 */
public interface ApplyContract {

    interface View extends IBaseView {

        void setRole(List<DataBean> list);

        void onCode();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void onRole();


        public abstract void onCode(String phone);

        public abstract void onSave(String userName, String wechatNum, String iphone, String vercoed, String inviteCode, String roleId,
                                    String industry, String county, String directTeam, String mailbox,
                                    String imgZheng, String imgFan, String imgShou, String cardId);
    }

}
