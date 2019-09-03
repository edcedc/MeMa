package com.yc.mema.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.CategoryContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/27
 * Time: 16:19
 */
public class CategoryPresenter extends CategoryContract.Presenter{

    @Override
    public void onRequest(String id, int pagetNumber) {
        CloudApi.goodSpuGetGoodsCategoryList(id)
                .doOnSubscribe(disposable -> {
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS) {
                            List<DataBean> list = baseResponseBeanResponse.body().result;
                            if (list != null) {
                                if (id.equals("0")){
                                    list.remove(list.size() - 1);
                                    mView.setLabel(list, list.get(0).getCategoryId(), list.get(0).getCategoryName());
                                }else {
                                    mView.setData(list);
                                }
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
