package com.yc.mema.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lzy.okgo.model.Response;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yc.mema.R;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.bean.AddressBean;
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

    private boolean isPermissionRequested;
    private SDKReceiver mReceiver;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationClient mLocClient;

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

        requestPermission();
        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        act.registerReceiver(mReceiver, iFilter);
//        Intent intent = new Intent();
//        intent.setAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
//        intent.setAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
//        intent.setAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
//        sendBroadcast(intent);

        // 定位初始化
        mLocClient = new LocationClient(act);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocClient.setLocOption(option);
//        mLocClient.start();
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
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE
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
        mLocClient.start();
//        String sessionId = ShareSessionIdCache.getInstance(act).getSessionId();
//        String userId = ShareSessionIdCache.getInstance(act).getUserId();
//        if (!StringUtils.isEmpty(sessionId) && !StringUtils.isEmpty(userId)) {
//            info();
//        } else {
//            startNext();
//        }
//        ShareIsLoginCache.getInstance(act).save(true);
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        // 取消监听 SDK 广播
        act.unregisterReceiver(mReceiver);
    }

    /**
     * Android6.0之后需要动态申请权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {

            isPermissionRequested = true;

            ArrayList<String> permissionsList = new ArrayList<>();

            String[] permissions = {
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };

            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != act.checkSelfPermission(perm)) {
                    permissionsList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }

            if (!permissionsList.isEmpty()) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }
    }

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    private class SDKReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                // 开放鉴权错误信息描述
                LogUtils.e("key 验证出错! 错误码 :"
                        + intent.getIntExtra(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, 0)
                        + " ; 错误信息 ："
                        + intent.getStringExtra(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_MESSAGE));
            } else if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
//                showToast("key 验证成功! 功能可以正常使用");
                LogUtils.e("key 验证成功! 功能可以正常使用");

            } else if (action.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                LogUtils.e("网络出错");
            }
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            LogUtils.e(location.getLatitude(), location.getLongitude(),
                    location.getProvince(),  location.getCity(),
                    location.getAddrStr());
            Address address = location.getAddress();
            AddressBean.getInstance().setLocation(location.getLongitude());
            AddressBean.getInstance().setLatitude(location.getLatitude());
            AddressBean.getInstance().setCountry(address.country);
            AddressBean.getInstance().setProvince(address.province);
            AddressBean.getInstance().setCity(address.city);
            AddressBean.getInstance().setDistrict(address.district);
//            EventBus.getDefault().post(new LocationInEvent());
            if (address != null){
                mLocClient.stop();

                String sessionId = ShareSessionIdCache.getInstance(act).getSessionId();
                String userId = ShareSessionIdCache.getInstance(act).getUserId();
                if (!StringUtils.isEmpty(sessionId) && !StringUtils.isEmpty(userId)) {
                    info();
                } else {
                    startNext();
                }
                ShareIsLoginCache.getInstance(act).save(true);
            }
        }
    }
}
