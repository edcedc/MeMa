package com.yc.mema.presenter;

import com.blankj.utilcode.util.StringUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.event.RefreshOrderListInEvent;
import com.yc.mema.impl.EvaluateContract;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/12
 * Time: 19:06
 */
public class EvaluatePresenter extends EvaluateContract.Presenter{
    @Override
    public void onSubmit(String goodId, String orderId, String toString, List<LocalMedia> localMediaList, float rating) {
        if (StringUtils.isEmpty(toString) && localMediaList.size() == 0 && rating == 0){
            return;
        }
        CloudApi.goodSpuSaveGoodsDiscuss(goodId, orderId, (int) rating, toString, localMediaList)
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
                            EventBus.getDefault().post(new RefreshOrderListInEvent());
                            act.finish();
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
