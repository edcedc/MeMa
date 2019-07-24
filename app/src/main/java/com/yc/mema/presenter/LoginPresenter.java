package com.yc.mema.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.impl.LoginContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 17:14
 */
public class LoginPresenter extends LoginContract.Presenter{
    @Override
    public void code(String phone) {
        if (StringUtils.isEmpty(phone)) {
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
    }

    @Override
    public void login(String phone, String code, String pwd, boolean checked, int mPosition) {
        if (StringUtils.isEmpty(phone)) {
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }

        if (mPosition == 0) {
            if (StringUtils.isEmpty(pwd)) {
                showToast(act.getString(R.string.please_pwd3));
                return;
            }
        }else {
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone)) {
                showToast(act.getString(R.string.error_));
                return;
            }
            if (!checked) {
                showToast(act.getString(R.string.error_1));
                return;
            }
        }
    }
}
