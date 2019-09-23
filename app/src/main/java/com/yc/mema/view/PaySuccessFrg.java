package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FPaySuccessBinding;
import com.yc.mema.view.act.ShopDescAct;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 21:16
 *  支付成功
 */
public class PaySuccessFrg extends BaseFragment<BasePresenter, FPaySuccessBinding> implements View.OnClickListener {

    private double price;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {
        price = bundle.getDouble("price");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_pay_success;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.pay_success));
        mB.tvLook.setOnClickListener(this);
        mB.tvShopping.setOnClickListener(this);
        mB.tvPrice.setText("¥" + price);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_look:
                UIHelper.startOrderListAct(0);
                break;
            case R.id.tv_shopping:
                UIHelper.startShopAct();
                ActivityUtils.finishActivity(ShopDescAct.class);
                break;
        }
    }
}
