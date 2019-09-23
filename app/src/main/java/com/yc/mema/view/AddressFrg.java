package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.BR;
import com.yc.mema.R;
import com.yc.mema.adapter.AddressAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FAddressBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.event.LocationInEvent;
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
public class AddressFrg extends BaseFragment<InformationPresenter, FAddressBinding> implements InformationContract.View, View.OnClickListener {

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

                break;
            case AddressInEvent.USER_INFP_TYPE:
            case AddressInEvent.HARVEST_ADDRESS:
                mB.gpLocate.setVisibility(View.GONE);
                break;
            case AddressInEvent.APPLY_TYPE:

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
            mB.gpLocate.setVisibility(View.GONE);
            mB.tvAll.setText(sb.toString());
            this.regionLevel = regionLevel;
            if (regionLevel >= 3){
                this.addressEnd = address;
                this.parentId = parentId;
                adapter.setmPosition(position);
                adapter.notifyDataSetChanged();
            }else {
                sb.append(address).append(" ");
                sbId.append(parentId).append(",");
                mPresenter.onRequest(parentId);
            }
            mB.tvAll.setText(sb.toString() + addressEnd);
            this.parentId = parentId;
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
        if (isLocation){
            String location = mB.tvLocation.getText().toString();
            addressEnd = location.split(" ")[1];
            LogUtils.e(addressEnd);
            EventBus.getDefault().post(new AddressInEvent(null, addressEnd, type, location));
        }else {
            sb.append(addressEnd);
            sbId.append(parentId);
            if (sbId.toString().indexOf("edison") != -1){
                sbId = sbId.delete(sbId.toString().length() - 7, sbId.toString().length());
                addressEnd = sb.toString().split(" ")[1];
            }
            LogUtils.e(sbId.toString());
            EventBus.getDefault().post(new AddressInEvent(sbId.toString(), addressEnd, type, mB.tvAll.getText().toString()));
        }
        pop();
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        listBean.clear();
        if (regionLevel == 2 && type == 0){
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
           /* AddressBean.getInstance().setLocation(location.getLongitude());
            AddressBean.getInstance().setLatitude(location.getLatitude());
            AddressBean.getInstance().setCountry(address.country);
            AddressBean.getInstance().setProvince(address.province);
            AddressBean.getInstance().setCity(address.city);
            AddressBean.getInstance().setDistrict(address.district);
            EventBus.getDefault().post(new LocationInEvent());*/

            mB.tvLocation.setText(address.city + " " + address.district);
            if (address != null){
                mLocClient.stop();
            }
        }
    }

}
