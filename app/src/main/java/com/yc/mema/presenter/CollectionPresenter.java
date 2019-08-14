package com.yc.mema.presenter;

import android.os.Handler;

import com.lzy.okgo.model.Response;
import com.yc.mema.bean.BaseListBean;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.CollectionContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 16:18
 */
public class CollectionPresenter extends CollectionContract.Presenter {
    @Override
    public void onRequest(int type, int pagetNumber) {
        String url = null;
        switch (type) {
            case 0:
                url = CloudApi.videoGetVideoCollList;
                break;
            case 1:
                url = CloudApi.informationGetInfoPraList;
                break;
            case 2:
                url = CloudApi.welfareGetWelCollectList;
                break;
        }
        CloudApi.list(pagetNumber, url)
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<BaseListBean<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<BaseListBean<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS) {
                            BaseListBean<DataBean> data = baseResponseBeanResponse.body().result;
                            if (data != null) {
                                List<DataBean> list = data.getList();
                                if (list != null) {
                                    mView.setData(list);
                                }
                                mView.hideLoading();
                                mView.setRefreshLayoutMode(data.getTotalCount());
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
    public void onDel(List<DataBean> list) {
        StringBuilder sb = new StringBuilder();
        int type = 0;
        for (DataBean bean : list){
            switch (bean.getType()){
                case 0:
                    sb.append(bean.getVideoId()).append(",");
                    break;
                case 1:
                    sb.append(bean.getInfoId()).append(",");
                    break;
                case 2:
                    sb.append(bean.getWelId()).append(",");
                    break;
            }
            type = bean.getType();
        }
        switch (type){
            case 1:
                setInfo(sb.toString(), list);
                break;
            case 2:
                setGift(sb.toString(), list);
                break;
        }
    }

    private void setGift(String ids, List<DataBean> list){
        CloudApi.welfareWelCollect(ids, 0)
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
                            mView.setDel(list);
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

    private void setInfo(String ids, List<DataBean> list){
        CloudApi.informationInfoPraise(ids, 0)
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
                            mView.setDel(list);
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
