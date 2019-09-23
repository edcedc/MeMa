package com.yc.mema.view.PopupView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.yc.mema.R;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/5
 * Time: 17:45
 *  商品筛选价格
 */
public class ShopPriceScreenView extends PartShadowPopupView {

    public ShopPriceScreenView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.p_shop_price_screen;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        AppCompatEditText etDi = findViewById(R.id.et_di);
        AppCompatEditText etGao = findViewById(R.id.et_gao);
        findViewById(R.id.bt_submit).setOnClickListener(view1 -> {
            int di = 0;
            int gao = 0;
            String sDi = etDi.getText().toString().trim();
            if (!StringUtils.isEmpty(sDi)){
                di = Integer.valueOf(sDi);
            }
            String sGao = etGao.getText().toString().trim();
            if (!StringUtils.isEmpty(sGao)){
                gao = Integer.valueOf(sGao);
            }
            if (di == 0 || gao == 0){
                ToastUtils.showShort("请选择区间");
                return;
            }else if (gao < di){
                ToastUtils.showShort("选择错误");
                return;
            }else {
                listener.onClick(di, gao);
            }
            dismiss();
        });
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
    }

    private onShopPriceScreenListener listener;
    public interface onShopPriceScreenListener{
        void onClick(int di, int gao);
    }
    public void setShopPriceScreenListener(onShopPriceScreenListener listener){
        this.listener = listener;
    }

}
