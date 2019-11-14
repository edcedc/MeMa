package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.ImmediatelyContract;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 15:58
 */
public class ImmediatelyPresenter extends ImmediatelyContract.Presenter{

    @Override
    public void onAddress() {
        CloudApi.list2(CloudApi.userGetUserAddresses)
                .doOnSubscribe(disposable -> { })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            List<DataBean> result = baseResponseBeanResponse.body().result;
                            for (DataBean bean : result){
                                if (bean.getStatus() == 2){
                                    mView.setAddress(bean);
                                    break;
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

                    }
                });
    }

    @Override
    public void onOrder(String id, String addressId, String sku, int skuNum, double allPrice, String orderId, String businessId, String skuId) {

        if (StringUtils.isEmpty(addressId)){
            showToast(act.getString(R.string.mema20));
            return;
        }
        CloudApi.goodSpuSaveGoodsOrder(id, addressId, sku, skuNum, allPrice, businessId, skuId)
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
                            mView.setOrder(result.getOrderId());
                        }else {
                            showToast(baseResponseBeanResponse.body().description);
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
}
