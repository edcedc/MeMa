package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.impl.HelpContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 19:48
 */
public class HelpPresenter extends HelpContract.Presenter {
    @Override
    public void onSubmit(String text, String phone) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(phone)){
            showToast(act.getString(R.string.error_));
            return;
        }
    }
}
