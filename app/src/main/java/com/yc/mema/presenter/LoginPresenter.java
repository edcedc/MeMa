package com.yc.mema.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.base.User;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.BaseResponseSuccessBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.LoginContract;
import com.yc.mema.utils.cache.ShareSessionIdCache;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 17:14
 */
public class LoginPresenter extends LoginContract.Presenter{
    @Override
    public void code(String phone) {
        if (StringUtils.isEmpty(phone)) {
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }
        CloudApi.userSendVcode(phone,1)
                .doOnSubscribe(disposable -> {mView.showLoading();})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            mView.onCode();
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

    @Override
    public void login(String phone, String code, String pwd, boolean checked, int mPosition) {
        if (StringUtils.isEmpty(phone)) {
            showToast(act.getString(R.string.error_phone1));
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showToast(act.getString(R.string.error_phone));
            return;
        }

        if (mPosition == 0) {
            if (StringUtils.isEmpty(pwd)) {
                showToast(act.getString(R.string.please_pwd3));
                return;
            }
            CloudApi.userLogin(phone, pwd)
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
                                JSONObject user = data.optJSONObject("user");
                                User.getInstance().setUserObj(user);
                                User.getInstance().setLogin(true);
                                ShareSessionIdCache.getInstance(Utils.getApp()).save(data.optString("token"));
                                ShareSessionIdCache.getInstance(Utils.getApp()).saveUserId(user.optString("userId"));
                                mView.onLogin(user);
                            }
                            showToast(jsonObject.optString("description"));
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
        }else {
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
                showToast(act.getString(R.string.error_));
                return;
            }
            if (!checked) {
                showToast(act.getString(R.string.error_1));
                return;
            }
            CloudApi.userResgist(phone, pwd, code)
                    .doOnSubscribe(disposable -> {})
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<BaseResponseBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mView.addDisposable(d);
                        }

                        @Override
                        public void onNext(Response<BaseResponseBean> baseResponseBeanResponse) {
                            if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                                mView.onResgist(phone, pwd);
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
}
