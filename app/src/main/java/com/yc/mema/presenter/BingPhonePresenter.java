package com.yc.mema.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.impl.BingPhoneContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 17:08
 */
public class BingPhonePresenter extends BingPhoneContract.Presenter {
    @Override
    public void code(String phone) {
        if (StringUtils.isEmpty(phone)) {
            showToast(act.getString(R.string.please_phone));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
    }

    @Override
    public void login(String phone, String code) {
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone)) {
            showToast(act.getString(R.string.error_phone1));
            return;
        }
    }
}
