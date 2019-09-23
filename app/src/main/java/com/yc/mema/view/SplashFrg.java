package com.yc.mema.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;
import com.yc.mema.R;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.bean.BaseListBean;
import com.yc.mema.bean.BaseResponseBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.callback.Code;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FSplashBinding;
import com.yc.mema.utils.GlideImageLoader;
import com.yc.mema.utils.cache.ShareIsLoginCache;
import com.yc.mema.utils.cache.ShareSessionIdCache;

import java.util.ArrayList;
import java.util.List;

import com.yc.mema.base.BaseFragment;
import com.yc.mema.weight.RuntimeRationale;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：yc on 2018/6/15.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class SplashFrg extends BaseFragment<BasePresenter, FSplashBinding> implements OnBannerListener {

    public static SplashFrg newInstance() {
        Bundle args = new Bundle();
        SplashFrg fragment = new SplashFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();

    private final int mHandle_splash = 0;
    private final int mHandle_permission = 1;

    private Activity act;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_splash;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        setSofia(true);
        act = getActivity();
        if (!ShareIsLoginCache.getInstance(act).getIsLogin()) {
            getDuideList();
        } else {
            handler.sendEmptyMessage(mHandle_permission);
        }
        mB.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == listBean.size() - 1) {
                    handler.sendEmptyMessageDelayed(mHandle_permission, 1000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getDuideList() {
        CloudApi.list2(CloudApi.guideGetDuideList)
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<BaseResponseBean<List<DataBean>>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Response<BaseResponseBean<List<DataBean>>> baseResponseBeanResponse) {
                        if (baseResponseBeanResponse.body().code == Code.CODE_SUCCESS) {
                            List<DataBean> list = baseResponseBeanResponse.body().result;
                            if (list != null && list.size() != 0) {
                                listBean.addAll(list);
                                mB.banner.setImages(listBean)
                                        .setImageLoader(new GlideImageLoader())
                                        .setOnBannerListener(SplashFrg.this::OnBannerClick)
                                        .setBannerAnimation(DefaultTransformer.class)
                                        .setBannerStyle(BannerConfig.NOT_INDICATOR)
                                        .start();
                            } else {
                                handler.sendEmptyMessage(mHandle_permission);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        SplashFrg.this.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case mHandle_splash:
                    startNext();
                    break;
                case mHandle_permission:
                    setHasPermission();
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    /**
     * 设置权限
     */
    private void setHasPermission() {
        AndPermission.with(SplashFrg.this)
                .runtime()
                .permission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,//写入外部存储, 允许程序写入外部存储，如SD卡
                        Manifest.permission.CAMERA,//拍照权限, 允许访问摄像头进行拍照
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .rationale(new RuntimeRationale())
                .onGranted(permissions -> setPermissionOk())
                .onDenied(permissions -> {
                    if (AndPermission.hasAlwaysDeniedPermission(SplashFrg.this, permissions)) {
                        showSettingDialog(act, permissions);
                    } else {
                        setPermissionCancel();
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, (dialog, which) -> setPermission())
                .setNegativeButton(R.string.cancel, (dialog, which) -> setPermissionCancel())
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(() -> {
                    setHasPermission();
//                        ToastUtils.showShort("用户从设置页面返回。");
                })
                .start();
    }

    /**
     * 权限有任何一个失败都会走的方法
     */
    private void setPermissionCancel() {
        act.finish();
    }

    /**
     * 权限都成功
     */
    private void setPermissionOk() {
        String sessionId = ShareSessionIdCache.getInstance(act).getSessionId();
        String userId = ShareSessionIdCache.getInstance(act).getUserId();
        if (!StringUtils.isEmpty(sessionId) && !StringUtils.isEmpty(userId)) {
            info();
        } else {
            startNext();
        }
        ShareIsLoginCache.getInstance(act).save(true);
    }

    private void info(){
        CloudApi.userFindByUser()
                .doOnSubscribe(disposable -> {})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        SplashFrg.this.addDisposable(d);
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.optInt("code") == Code.CODE_SUCCESS){
                            JSONObject data = jsonObject.optJSONObject("result");
                            User.getInstance().setUserObj(data);
                            User.getInstance().setLogin(true);
                        }else {
                            ShareSessionIdCache.getInstance(act).remove();
                        }
                        startNext();
                    }

                    @Override
                    public void onError(Throwable e) {
                        SplashFrg.this.onError(e);
                        ShareSessionIdCache.getInstance(act).remove();
                        startNext();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void startNext() {
        UIHelper.startMainAct();
//        UIHelper.startLoginAct();
        ActivityUtils.finishAllActivities();
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
