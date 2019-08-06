package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.impl.ReleaseContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/31
 * Time: 16:55
 */
public class ReleasePresenter extends ReleaseContract.Presenter{
    @Override
    public void onRelease(String text, String path) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(path)) {
            showToast(act.getString(R.string.please_pwd3));
            return;
        }
    }
}
