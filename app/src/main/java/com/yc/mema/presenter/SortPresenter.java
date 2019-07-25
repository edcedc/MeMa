package com.yc.mema.presenter;

import com.yc.mema.bean.DataBean;
import com.yc.mema.impl.SortContract;
import com.yc.mema.weight.sort.PinyinComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 18:36
 */
public class SortPresenter extends SortContract.Presenter{


    @Override
    public void onRequest(int pagetNumber) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i<10;i++){
            switch (i){
                case 0:
                    DataBean bean = new DataBean();
                    bean.setName("哈哈");
                    list.add(bean);
                    break;
                case 1:
                    DataBean bean1 = new DataBean();
                    bean1.setName("呃呃");
                    list.add(bean1);
                    break;
                case 2:
                    DataBean bean2 = new DataBean();
                    bean2.setName("请求");
                    list.add(bean2);
                    break;
                default:
                    DataBean bean12 = new DataBean();
                    bean12.setName("阿松大大大啊");
                    list.add(bean12);
                    break;
            }
        }
        mView.setData(list);
        mView.hideLoading();
    }
}
