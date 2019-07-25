package com.yc.mema.presenter;

import android.os.Handler;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.InformationContract;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void mema(String time, String mema) {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(mema)){
            showToast(act.getString(R.string.error_));
            return;
        }
    }

    @Override
    public void birthday(String birthday) {

    }

    @Override
    public void sex(int type) {
        if (type == -1){
            return;
        }

    }

    @Override
    public void onRequest(int pagetNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i =0;i<5;i++){
            list.add(new DataBean());
        }
        mView.setData(list);
        mView.hideLoading();
    }

}
