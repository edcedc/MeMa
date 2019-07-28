package com.yc.mema.presenter;

import android.os.Handler;

import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.SearchGiftContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 20:59
 */
public class SearchGiftPresenter extends SearchGiftContract.Presenter {
    @Override
    public void onRequest(String text, int pagetNumber) {

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               List<DataBean> list = new ArrayList<>();
               for (int i = 0;i<3;i++){
                   list.add(new DataBean());
               }
               mView.setData(list);
               mView.hideLoading();
           }
       }, 500);
    }

}
