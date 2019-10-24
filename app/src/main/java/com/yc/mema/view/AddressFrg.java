package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;
import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.adapter.AddressAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FAddressBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 12:01
 *  设置地址
 */
public class AddressFrg extends BaseFragment<InformationPresenter, FAddressBinding> implements InformationContract.View, View.OnClickListener, OnGetGeoCoderResultListener {

    public static AddressFrg newInstance() {

        Bundle args = new Bundle();

        AddressFrg fragment = new AddressFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private List<DataBean> listBean = new ArrayList<>();
    private AddressAdapter adapter;
    private StringBuffer sb = new StringBuffer();
    private StringBuffer sbId = new StringBuffer();
    private String addressEnd = "";
    private String parentId;
    private int regionLevel;
    private int type;
    private MyLocationListenner myListener = new MyLocationListenner();
    private LocationClient mLocClient;
    private boolean isLocation = false;

    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_address;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_address), getString(R.string.submit1));
        switch (type){
            case AddressInEvent.GIFT_TYPE:
            case AddressInEvent.LIWU:
            case AddressInEvent.APPLY_TYPE:

                break;
            case AddressInEvent.USER_INFP_TYPE:
            case AddressInEvent.HARVEST_ADDRESS:
            case AddressInEvent.TENTRY:
                mB.gpLocate.setVisibility(View.GONE);
                break;
        }
        // 定位初始化
        mLocClient = new LocationClient(act);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        mLocClient.setLocOption(option);
        mLocClient.start();
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        mB.tvLocation.setOnClickListener(this);
        if (adapter == null){
            adapter = new AddressAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        showLoadDataing();
        mPresenter.onRequest(null);
        adapter.setOnClickListener((parentId, address, regionLevel, position) -> {
            isLocation = false;
//            mB.gpLocate.setVisibility(View.GONE);
//            mB.tvAll.setText(sb.toString());
            this.regionLevel = regionLevel;
            /*if (regionLevel >= 3){
                this.addressEnd = address;
                this.parentId = parentId;
                adapter.setmPosition(position);
                adapter.notifyDataSetChanged();
            }else {
                sb.append(address).append(" ");
                sbId.append(parentId).append(",");
                mPresenter.onRequest(parentId);
            }*/
            this.parentId = parentId;
            mB.tvAll.setText(address);
            LogUtils.e(mB.tvAll.getText().toString(), parentId);
            UIHelper.startAddress1Frg(this, type, parentId, address);
        });
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        if (StringUtils.isEmpty(addressEnd) && !isLocation)return;
        switch (type){
            case AddressInEvent.GIFT_TYPE:

                break;
            case AddressInEvent.USER_INFP_TYPE:

                break;
            case AddressInEvent.APPLY_TYPE:

                break;
            case AddressInEvent.HARVEST_ADDRESS:

                break;
        }

        String[] split;
        if (isLocation){
            split = mB.tvLocation.getText().toString().split(" ");
        }else {
            split = mB.tvAll.getText().toString().split(" ");
        }
        // Geo搜索
        mSearch.geocode(new GeoCodeOption()
                .city(split[1])
                .address(split[2]));
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        listBean.clear();
        if (regionLevel == 2 && type == AddressInEvent.GIFT_TYPE){
            DataBean bean = new DataBean();
            bean.setRegionName("不限区域");
            bean.setRegionLevel(3);
            bean.setRegionId("edison");
            listBean.add(0, bean);
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveUser() {
        pop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_location:
                if (!isLocation){
                    mB.tvLocation.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y19, null),null,
                            act.getResources().getDrawable(R.mipmap.y18, null), null);
                }else {
                    mB.tvLocation.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y19, null),null,
                            null, null);
                }
                isLocation = !isLocation;
                break;
        }
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("抱歉，未能找到结果");
            return;
        }
        AddressBean.getInstance().setLocation(result.getLocation().longitude);
        AddressBean.getInstance().setLatitude(result.getLocation().latitude);
        String[] split;
        if (isLocation){
            split = mB.tvLocation.getText().toString().split(" ");
        }else {
            sbId.append(parentId);
            LogUtils.e(sbId);
            if (sbId.toString().indexOf("edison") != -1){
                sbId = sbId.delete(sbId.toString().length() - 7, sbId.toString().length());
            }else {
                sbId.append(sbId.toString().split(",")[2]);
            }
            split = mB.tvAll.getText().toString().split(" ");
        }
        AddressBean.getInstance().setProvince(split[0]);
        AddressBean.getInstance().setCity(split[1]);
        AddressBean.getInstance().setDistrict(split[2]);
        AddressBean.getInstance().setAddress(AddressBean.getInstance().getProvince() + " " + AddressBean.getInstance().getCity() + " " + AddressBean.getInstance().getDistrict());
        LogUtils.e( AddressBean.getInstance().getProvince(), AddressBean.getInstance().getCity(),AddressBean.getInstance().getDistrict(),
                AddressBean.getInstance().getLatitude(), AddressBean.getInstance().getLocation(),
        sbId);
        EventBus.getDefault().post(new AddressInEvent(type, sbId.toString()));
        act.finish();
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

    }

    /**
     * 定位SDK监听函数
     */
    private class MyLocationListenner extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            LogUtils.e(location.getLatitude(), location.getLongitude(),
                    location.getProvince(),  location.getCity(), location.getDistrict(),
                    location.getAddrStr());
            Address address = location.getAddress();

            String province = address.province;
            if (StringUtils.isEmpty(province)){
                mB.tvLocation.setText(AddressBean.getInstance().getProvince() + " " + AddressBean.getInstance().getCity() + " " + AddressBean.getInstance().getDistrict());
            }else {
                mB.tvLocation.setText(address.province + " " + address.city + " " + address.district);
            }

            if (address != null){
                mLocClient.stop();
            }
        }
    }

}
