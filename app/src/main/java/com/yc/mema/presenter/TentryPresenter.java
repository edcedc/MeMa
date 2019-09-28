package com.yc.mema.presenter;

import android.widget.ListView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Response;
import com.yc.mema.R;
import com.yc.mema.adapter.TentryAdapter;
import com.yc.mema.base.TentryBean;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.event.TentryInEvent;
import com.yc.mema.impl.TentryContract;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/26
 * Time: 16:13
 */
public class TentryPresenter extends TentryContract.Presenter {

    @Override
    public void onSure(String name, String phone, String userId, String num, String bankName, String bankPhone, String bankId, String address, String addressDesc, int type, String category, String shopArea, String shopScope, boolean isShop, boolean isMerchants) {
        type = 0;
        if (isShop){
            type = 1;
        }
        if (isMerchants){
            type = 2;
        }
        if (isShop && isMerchants){
            type = 3;
        }
        if (StringUtils.isEmpty(name) ||StringUtils.isEmpty(phone) ||StringUtils.isEmpty(userId) ||StringUtils.isEmpty(num) ||StringUtils.isEmpty(bankName) ||StringUtils.isEmpty(bankPhone) ||StringUtils.isEmpty(bankId) ||StringUtils.isEmpty(address) ||StringUtils.isEmpty(addressDesc) || type == 0 ||StringUtils.isEmpty(category) ||StringUtils.isEmpty(shopArea)){
            showToast(act.getString(R.string.error_));
//            return;
        }
        TentryBean.getInstance().name = name;
        TentryBean.getInstance().phone = phone;
        TentryBean.getInstance().userId = userId;
        TentryBean.getInstance().num = num;
        TentryBean.getInstance().bankName = bankName;
        TentryBean.getInstance().bankPhone = bankPhone;
        TentryBean.getInstance().bankId = bankId;
        TentryBean.getInstance().address = address;
        TentryBean.getInstance().addressDesc = addressDesc;
        TentryBean.getInstance().type = type;
        TentryBean.getInstance().category = category;
        TentryBean.getInstance().shopArea = shopArea;
        TentryBean.getInstance().shopScope = shopScope;
        EventBus.getDefault().post(new TentryInEvent(2, 1));
        LogUtils.e(name, phone, userId, num, bankName, bankPhone, bankId, address, addressDesc, type, category, shopArea, shopScope);
//        mView.setData(name, phone, userId, num, bankName, bankPhone, bankId, address, addressDesc, type, category, shopArea, shopScope);
    }

    @Override
    public void onList(ListView listView) {
        int[] img ={R.mipmap.zhengmian,R.mipmap.fanmian,R.mipmap.yingyezhizhao,R.mipmap.weishengxuke,R.mipmap.shipinjingying};
        String[] str = {act.getString(R.string.mema31),act.getString(R.string.mema32),act.getString(R.string.business_license),act.getString(R.string.mema33),act.getString(R.string.mema34) };
        List<DataBean> list = new ArrayList<>();
        for (int i = 0;i < str.length; i++){
            DataBean bean = new DataBean();
            bean.setImg(img[i]);
            bean.setTitle(str[i]);
            list.add(bean);
        }
        TentryAdapter adapter = new TentryAdapter(act, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onTwoSure(String yingy, String scsf, String sfz, String sff, String weis, String ship) {
        if (StringUtils.isEmpty(yingy) || StringUtils.isEmpty(sfz) || StringUtils.isEmpty(sff) || StringUtils.isEmpty(weis) || StringUtils.isEmpty(ship)){
            showToast(act.getString(R.string.error_3));
            return;
        }
        List<LocalMedia> localMediaList = new ArrayList<>();
        localMediaList.add(new LocalMedia(yingy, 0, 0, ""));
//        localMediaList.add(new LocalMedia(scsf, 0, 0, ""));
        localMediaList.add(new LocalMedia(sfz, 0, 0, ""));
        localMediaList.add(new LocalMedia(sff, 0, 0, ""));
        localMediaList.add(new LocalMedia(weis, 0, 0, ""));
        localMediaList.add(new LocalMedia(ship, 0, 0, ""));

        CloudApi.businessSaveBusiness(localMediaList)
                .doOnSubscribe(disposable -> {mView.showLoading();})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<String>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<String>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS){
                              EventBus.getDefault().post(new TentryInEvent(3, 2));
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
    public void onHelpList() {
        CloudApi.list2(CloudApi.businessGetGoodsHelps)
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
                            if (list != null && list.size() != 0){
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

}
