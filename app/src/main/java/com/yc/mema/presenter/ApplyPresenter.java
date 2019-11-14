package com.yc.mema.presenter;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.ApplyContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/19
 * Time: 15:39
 */
public class ApplyPresenter extends ApplyContract.Presenter{
    @Override
    public void onRole() {
        CloudApi.roleGetRoleSelect()
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
                            try {
                                JSONArray array = new JSONArray(jsonObject.optString("result"));
                                List<DataBean> list = new ArrayList<>();
                                for (int i = 0;i < array.length();i++){
                                    JSONObject object = array.optJSONObject(i);
                                    DataBean bean = new DataBean();
                                    bean.setValue(object.optString("value"));
                                    bean.setLabel(object.optString("label"));
                                    bean.setRoleId(object.optString("roleId"));
                                    list.add(bean);
                                }
                                mView.setRole(list);
                            } catch (JSONException e) {
                                e.printStackTrace();
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
    public void onCode(String phone) {
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
    public void onSave(String userName, String wechatNum, String iphone, String vercoed, String inviteCode, String roleId, String industry, String county, String directTeam, String mailbox, String imgZheng, String imgFan, String imgShou, String cardId) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(iphone) || StringUtils.isEmpty(roleId) || StringUtils.isEmpty(county) || StringUtils.isEmpty(vercoed)){
            showToast(act.getString(R.string.error_));
            return;
        }
//        mailbox
        if (!RegexUtils.isEmail(mailbox)){
            showToast(act.getString(R.string.please_email1));
            return;
        }

        CloudApi.agentSaveAgent(userName, wechatNum, iphone, vercoed, inviteCode, roleId, industry, county, directTeam, mailbox, imgZheng, imgFan, imgShou, cardId)
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
                            act.onBackPressed();
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
