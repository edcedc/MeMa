package com.yc.mema.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.OrderDescContract;
import com.yc.mema.utils.Constants;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/12
 * Time: 14:54
 */
public class OrderDescPresenter extends OrderDescContract.Presenter{
    @Override
    public void onRequest(String id) {
        CloudApi.goodSpuGetGoodsOrder(id)
                .doOnSubscribe(disposable -> {mView.showLoading();})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            DataBean result = baseResponseBeanResponse.body().result;
                            mView.setData(result);
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
    public void onWxPay(String orderId, int position) {
        CloudApi.payWeChatPay(orderId)
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                            switch (position){
                                case 0:
                                    ((BaseActivity)act).wxPay(jsonObject.optJSONObject("result"));
                                    break;
                            }
                        }else {
                            showToast(jsonObject.optString("description"));
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
    public void onUpdateOrder(String orderId, int type) {
        CloudApi.goodSpuSaveGoodsOrder(orderId, type)
                .doOnSubscribe(disposable -> {mView.showLoading();})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            mView.setRefresh();
                        }
                        showToast(baseResponseBeanResponse.body().description);
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
