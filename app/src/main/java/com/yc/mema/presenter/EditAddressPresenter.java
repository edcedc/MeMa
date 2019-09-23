package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.EditAddressContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/9
 * Time: 17:12
 */
public class EditAddressPresenter extends EditAddressContract.Presenter{

    @Override
    public void onAdd(String id, String name, String phone, String location, String desc, String parentId) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(location) || StringUtils.isEmpty(desc)){
            showToast(act.getString(R.string.error_));
            return;
        }
        CloudApi.userSaveUserAddress(id, name, phone, location, desc, parentId)
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
                            mView.setEdit();
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
