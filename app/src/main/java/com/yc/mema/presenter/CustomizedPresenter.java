package com.yc.mema.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mema.bean.BaseListBean;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.CustomizedContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/17
 * Time: 18:49
 */
public class CustomizedPresenter extends CustomizedContract.Presenter{

    @Override
    public void onDesc() {
        List<DataBean> list = new ArrayList<>();
        for (int i =0;i<5;i++){
            DataBean bean = new DataBean();
            bean.setImage("https://wx3.sinaimg.cn/mw690/78a9167dgy1g6vqt27xilj212c0hsdp6.jpg");
            list.add(bean);
        }
        mView.setBanner(list);
    }

    @Override
    public void onHot() {
        List<DataBean> list = new ArrayList<>();
        for (int i =0;i<5;i++){
            DataBean bean = new DataBean();
            bean.setImage("https://wx3.sinaimg.cn/mw690/78a9167dgy1g6vqt27xilj212c0hsdp6.jpg");
            list.add(bean);
        }
        mView.setHot(list);
    }

    @Override
    public void onRequest(int pagerNumber) {
        CloudApi.welfareGetWelfareList(null, null, 0, pagerNumber)
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<BaseListBean<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<BaseListBean<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS) {
                            BaseListBean<DataBean> data = baseResponseBeanResponse.body().result;
                            if (data != null) {
                                List<DataBean> list = data.getList();
                                if (list != null) {
                                    mView.setData(list);
                                }
                                mView.setRefreshLayoutMode(data.getTotalCount());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }

}
