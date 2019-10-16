package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.mema.R;
import com.yc.mema.adapter.ConfirmOrdersAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FImmediatelyBinding;
import com.yc.mema.event.PayInEvent;
import com.yc.mema.event.ShopAddressInEvent;
import com.yc.mema.impl.ImmediatelyContract;
import com.yc.mema.presenter.ImmediatelyPresenter;
import com.yc.mema.view.bottomFrg.PayBottomFrg;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/29
 * Time: 14:45
 *  立即购买
 */
public class ImmediatelyFrg extends BaseFragment<ImmediatelyPresenter, FImmediatelyBinding> implements ImmediatelyContract.View, View.OnClickListener {

    private String id;
    private String sku;
    private String addressId;
    private List<DataBean> listBean;
    private ConfirmOrdersAdapter adapter;
    private int type = 0;//0默认立即购买
    private int skuNum;
    private double allPrice;
    private PayBottomFrg payBottomFrg;
    private String orderId;
    private int payPosition;
    private String businessId;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
        listBean = new Gson().fromJson(bundle.getString("list"), new TypeToken<ArrayList<DataBean>>() {}.getType());
        if (type == 0){
            businessId = listBean.get(0).getBusinessId();
            id = listBean.get(0).getGoodId();
            sku = listBean.get(0).getGoodSku();
            skuNum = listBean.get(0).getGoodNumber();
        }
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_immediately;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.confirmation_orders));
        mB.btSubmit.setOnClickListener(this);
        mB.tvAddress.setOnClickListener(this);
        mPresenter.onAddress();
        if (adapter == null){
            adapter = new ConfirmOrdersAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView, R.color.white);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.recyclerView.setAdapter(adapter);

        allPrice = 0;
        for (DataBean bean : listBean){
            allPrice = bean.getPrice() * bean.getGoodNumber();
        }
        mB.tvTotalPrice.setText("¥" + allPrice);
        mB.tvTotalFreight.setText("（运费¥0）");
        EventBus.getDefault().register(this);

        payBottomFrg = new PayBottomFrg();
        payBottomFrg.setOnClickListener(position -> {
            payPosition = position;
            switch (position){
                case 0:
                    mPresenter.onWxPay(orderId, position);
                    break;
                case 1:
                    break;
            }
        });
        Bundle bundle = new Bundle();
        bundle.putDouble("price", allPrice);
        payBottomFrg.setArguments(bundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_address:
                UIHelper.startShopAddressFrg(this);
                break;
            case R.id.bt_submit:
                if (!StringUtils.isEmpty(orderId)){
//                    showToast(act.getString(R.string.mema23));
//                    return;
                    switch (payPosition){
                        case 0:
                            mPresenter.onWxPay(orderId, payPosition);
                            break;
                    }
                }
                if (type == 0){
                    mPresenter.onOrder(id, addressId, sku, skuNum, allPrice, orderId, businessId);
                }
                break;
        }
    }


    @Override
    public void setAddress(DataBean bean) {
        addressId = bean.getAddressId();
        String userName = bean.getUserName();
        String iphone = bean.getIphone();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#999999"));
        SpannableString cText = new SpannableString("收件人：" + userName + "   " + iphone + "\n" +bean.getCounties() + bean.getAddress());
        cText.setSpan(colorSpan, bean.getUserName().length() + 7, bean.getUserName().length() + 7 + iphone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvAddress.setText(cText);
    }

    @Override
    public void setOrder(String orderId) {
        this.orderId = orderId;
        payBottomFrg.show(getChildFragmentManager(), "dialog");
    }

    @Subscribe
    public void onMainShopAddressInEvent(ShopAddressInEvent event){
        setAddress(event.bean);
    }


    @Subscribe
    public void onMainPayInEvent(PayInEvent event){
        UIHelper.startPaySuccessFrg(this, allPrice);
    }

}
