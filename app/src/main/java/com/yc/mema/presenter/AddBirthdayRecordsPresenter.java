package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.impl.AddBirthdayRecordsContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/26
 * Time: 17:03
 */
public class AddBirthdayRecordsPresenter extends AddBirthdayRecordsContract.Presenter {
    @Override
    public void add(String name, String hb) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(hb)){
            showToast(act.getString(R.string.error_));
            return;
        }
    }
}
