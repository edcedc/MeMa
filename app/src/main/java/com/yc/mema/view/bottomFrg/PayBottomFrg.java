package com.yc.mema.view.bottomFrg;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseBottomSheetFrg;
import com.yc.mema.bean.DataBean;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 17:05
 */
public class PayBottomFrg extends BaseBottomSheetFrg implements View.OnClickListener {

    private AppCompatTextView tvWx;
    private AppCompatTextView tvZfb;
    private double price;
    private AppCompatTextView tv_price;
    private int payType = -1;

    @Override
    protected void initParms(Bundle bundle) {
        price = bundle.getDouble("price");
    }

    @Override
    public int bindLayout() {
        return R.layout.p_pay;
    }

    @Override
    public void initView(View view) {
        tv_price = view.findViewById(R.id.tv_price);
        view.findViewById(R.id.tv_title).setOnClickListener(this);
        tvWx = view.findViewById(R.id.tv_wx);
        tvWx.setOnClickListener(this);
        tvZfb = view.findViewById(R.id.tv_zfb);
        tvZfb.setOnClickListener(this);
        view.findViewById(R.id.bt_submit).setOnClickListener(this);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
        SpannableString cText = new SpannableString("实付金额：" + "¥" + price);
        cText.setSpan(colorSpan, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_price.setText(cText);
    }

    public OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onPay(int position);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_title:
                dismiss();
                break;
            case R.id.tv_wx:
                payType = 0;
                tvWx.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.weixin1, null),
                        null, act.getResources().getDrawable(R.mipmap.xu2, null), null);
                tvZfb.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.zhifubao1, null),
                        null, act.getResources().getDrawable(R.mipmap.xu1, null), null);
                break;
            case R.id.tv_zfb:
                payType = 1;
                tvWx.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.weixin1, null),
                        null, act.getResources().getDrawable(R.mipmap.xu1, null), null);
                tvZfb.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.zhifubao1, null),
                        null, act.getResources().getDrawable(R.mipmap.xu2, null), null);
                break;
            case R.id.bt_submit:
                if (payType == -1){
                    showToast(getString(R.string.mema21));
                    return;
                }
                if (listener != null)listener.onPay(payType);
                dismiss();
                break;
        }
    }



}
