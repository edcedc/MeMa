package com.yc.mema;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.LogUtils;
import com.umeng.socialize.UMShareAPI;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.view.MainFrg;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private boolean isPermissionRequested;
    private SDKReceiver mReceiver;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationClient mLocClient;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
//        AndroidBug5497Workaround.assistActivity(this);
        setSwipeBackEnable(false);
        if (findFragment(MainFrg.class) == null) {
            loadRootFragment(R.id.fl_container, MainFrg.newInstance());
        }

        requestPermission();
        // 注册 SDK 广播监听者
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
//        Intent intent = new Intent();
//        intent.setAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
//        intent.setAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
//        intent.setAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
//        sendBroadcast(intent);

        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocClient.setLocOption(option);
//        mLocClient.start();

        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
        // IntentService, 必须在 AndroidManifest 中声明)
//        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), IntentService.class);
//        PushManager.getInstance().bindAlias(this, "999");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消监听 SDK 广播
        unregisterReceiver(mReceiver);
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
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                    permissionsList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }

            if (!permissionsList.isEmpty()) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            EventBus.getDefault().post(new CameraInEvent(requestCode, data));
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
            }
        }
    }
}
