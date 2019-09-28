package com.yc.mema.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mema.base.User;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.impl.FiveContract;
import com.yc.mema.utils.cache.ShareSessionIdCache;
import com.yc.mema.view.FiveFrg;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 15:09
 */
public class FivePresenter extends FiveContract.Presenter{

    @Override
    public void onInfo() {
        CloudApi.userFindByUser()
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
                            JSONObject data = jsonObject.optJSONObject("result");
                            User.getInstance().setUserObj(data);
                            User.getInstance().setLogin(true);
                            mView.setData(User.getInstance().getUserObj());
                        }else {
                            User.getInstance().setLogin(false);
                            ShareSessionIdCache.getInstance(act).remove();
                            mView.setDateError();
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
    public void onaUserAgent(FiveFrg fiveFrg) {
        CloudApi.agentGetUserAgent()
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<DataBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<DataBean>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            int handle = baseResponseBeanResponse.body().result.getHandle();
                            switch (handle){
                                case 0:
                                case 3:
                                    UIHelper.startApplyFrg(fiveFrg);
                                    break;
                                case 1:
                                    showToast("你已提交申请 正在审核");
                                    break;
                                case 2:
                                    showToast("你已成为代理人");
                                    break;
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
    public void onBusinessGetBusiness() {
        CloudApi.businessGetBusiness()
                .doOnSubscribe(disposable -> {})
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
                            int handle;
                            String reason = null;
                            if (result != null){
                                handle = result.getHandle();
                                reason = result.getReason();
                            }else {
                                handle = 3;
                            }
                            mView.setGetBusiness(handle, reason);
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

}
