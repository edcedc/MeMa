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

    }

    @Override
    public void onHot() {
        CloudApi.welfareGetWelfareList(null, null, 0, 1, 0, 0, 1)
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
                                    mView.setHot(list);
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

    @Override
    public void onRequest(int pagerNumber, int type) {
        CloudApi.welfareGetWelfareList(null, null, 0, pagerNumber, 0, 0, type)
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
