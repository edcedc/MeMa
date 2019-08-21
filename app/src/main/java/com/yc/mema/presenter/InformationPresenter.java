package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.base.User;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.InformationContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/23
 * Time: 20:12
 */
public class InformationPresenter extends InformationContract.Presenter {

    @Override
    public void submit(String head, String name, String time) {
        if (StringUtils.isEmpty(head)){
            showToast(act.getString(R.string.error_head));
            return;
        }
        if (StringUtils.isEmpty(name)){
            showToast(act.getString(R.string.error_nickname));
            return;
        }
        if (StringUtils.isEmpty(time)){
            showToast(act.getString(R.string.please_hp));
            return;
        }
        JSONObject userObj = User.getInstance().getUserObj();
        String mema = userObj.optString("mema");
        mema = mema.substring(mema.length() - 4, mema.length());
        mema = time + mema;
        userSaveUser(head, name, time, null, null, "M-" + mema.replaceAll("-", ""), null);
    }

    @Override
    public void head(String head) {
        if (StringUtils.isEmpty(head)){
            showToast(act.getString(R.string.error_head));
            return;
        }
        userSaveUser(head, null, null, null, null, null, null);
    }

    @Override
    public void name(String name) {
        if (StringUtils.isEmpty(name)){
            showToast(act.getString(R.string.error_nickname));
            return;
        }
        userSaveUser(null, name, null, null, null, null, null);
    }

    @Override
    public void mema(String time, String mema, int updataMema) {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(mema)){
            showToast(act.getString(R.string.error_));
            return;
        }
        String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match=pattern.matcher(mema);
        if(!match.matches()){
            showToast(act.getString(R.string.mema12));
            return;
        }
        try {
            JSONObject userObj = User.getInstance().getUserObj();
            userObj.put("updataMema", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] split = time.split("-");
        mema = "M-" + split[0] + split[1] + split[2] + mema;
        userSaveUser(null, null, null, null, null, mema, "1");
    }

    @Override
    public void birthday(String birthday) {
        userSaveUser(null, null, birthday, null, null, null, null);
    }

    @Override
    public void sex(int type) {
        if (type == -1)return;
        userSaveUser(null, null, null, type + "", null, null, null);
    }

    @Override
    public void onRequest(String parentId) {
        CloudApi.regionGetRegion(parentId)
                .doOnSubscribe(disposable -> {mView.showLoading();})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                            List<DataBean> list = baseResponseBeanResponse.body().result;
                            if (list != null && list.size() != 0){
                                for (DataBean bean : list){
                                    if (bean.getRegionId().equals("100")){
                                        list.remove(bean);
                                        break;
                                    }
                                }
                                mView.setData(list);
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
    public void address(String parentId) {
        userSaveUser(null, null, null, null, parentId, null, null);
    }

    private void userSaveUser(String head, String nickName, String birthday, String sex, String parentId, String mema, String updataMema){
        CloudApi.userSaveUser(head, nickName, birthday, sex, parentId, mema, updataMema)
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
                            try {
                                String s = new Gson().toJson(baseResponseBeanResponse.body().result);
                                User.getInstance().setUserObj(new JSONObject(s));
                                mView.onSaveUser();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
