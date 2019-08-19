package com.yc.mema.presenter;

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
    public void onSaveAgent(String name, String phone, String roleId, String county, String text) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(roleId) || StringUtils.isEmpty(county) || StringUtils.isEmpty(text)){
            showToast(act.getString(R.string.error_));
            return;
        }
        CloudApi.agentSaveAgent(name, phone, roleId, county, text)
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
