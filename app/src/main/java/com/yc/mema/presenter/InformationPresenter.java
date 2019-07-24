package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.impl.InformationContract;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 20:12
 */
public class InformationPresenter extends InformationContract.Presenter {

    @Override
    public void submit(String head, String name, String time) {
        if (StringUtils.isEmpty(head) || StringUtils.isEmpty(name) || StringUtils.isEmpty(time)){
            showToast(act.getString(R.string.error_));
            return;
        }
    }

    @Override
    public void head(String head) {
        if (StringUtils.isEmpty(head)){
            showToast(act.getString(R.string.error_head));
            return;
        }
    }

    @Override
    public void name(String name) {
        if (StringUtils.isEmpty(name)){
            showToast(act.getString(R.string.error_nickname));
            return;
        }
    }

}
