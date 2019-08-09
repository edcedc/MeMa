package com.yc.mema.presenter;

import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.adapter.LabelAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.BaseListBean;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.impl.OneContract;
import com.yc.mema.weight.WithScrollGridView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 16:58
 */
public class OnePresenter extends OneContract.Presenter {
    @Override
    public void onRequest(String county, String search, int itemize, int pagetNumber) {
        CloudApi.welfareGetWelfareList(county, search, itemize, pagetNumber)
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
    public void onBanner() {
        CloudApi.list2(CloudApi.welfareGetWelfareLunList)
                .doOnSubscribe(disposable -> {})
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
                            if (list != null){
                                mView.setBanner(list);
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
    public void onGridView(BaseFragment root, WithScrollGridView recyclerView) {
        String[] str = {act.getString(R.string.all), act.getString(R.string.cake), act.getString(R.string.eat), act.getString(R.string.drink),
                act.getString(R.string.play), act.getString(R.string.hHappy), act.getString(R.string.gift), act.getString(R.string.free)};
        int[] img = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,};

        List<DataBean> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            DataBean bean = new DataBean();
            bean.setImg(img[i]);
            bean.setTitle(str[i]);
            list.add(bean);
        }
        LabelAdapter adapter = new LabelAdapter(act, list);
        recyclerView.setAdapter(adapter);
    }
}
