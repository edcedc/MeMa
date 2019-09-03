package com.yc.mema.view.bottomFrg;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopSkuAdapter;
import com.yc.mema.base.BaseBottomSheetFrag;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/28
 * Time: 17:24
 */
public class ShopSkuBottonFrg extends BaseBottomSheetFrag implements View.OnClickListener {

    private AppCompatImageView ivImg;
    private AppCompatTextView tvPrice;
    private AppCompatTextView tvSales;
    private AppCompatTextView tvSku;
    private AppCompatTextView tvJia;
    private AppCompatTextView tvNum;
    private AppCompatTextView tvJian;
    private DataBean bean;

    private ShopSkuAdapter adapter;
    private JSONObject valueIds;
    private JSONObject sku;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:

                break;

        }
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
    }

    @Override
    public int bindLayout() {
        return R.layout.p_sku;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.bt_submit).setOnClickListener(this);
        ivImg = view.findViewById(R.id.iv_img);
        tvPrice = view.findViewById(R.id.tv_price);
        tvSales = view.findViewById(R.id.tv_sales);
        tvSku = view.findViewById(R.id.tv_sku);
        tvJia = view.findViewById(R.id.tv_jia);
        tvNum = view.findViewById(R.id.tv_num);
        tvJian = view.findViewById(R.id.tv_jian);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        List<DataBean> imgs = bean.getGoodSupImgs();
        if (imgs != null && imgs.size() != 0){
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + imgs.get(0).getAttachId(), ivImg);
        }
        double price = bean.getPrice();
        SpannableString hText = new SpannableString("¥" + price);
        hText.setSpan(new AbsoluteSizeSpan(8, true), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvPrice.setText(hText);

        List<DataBean> specList = bean.getSpecList();
        if (adapter == null){
            adapter = new ShopSkuAdapter(act, specList);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(act));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        recyclerView.setAdapter(adapter);

        try {
            JSONObject object = new JSONObject(bean.getSpecSku());
            valueIds = object.optJSONObject("valueIds");
            sku = object.optJSONObject("sku");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.setListener((list, valueId, b) -> {
            for (int i = 0;i < specList.size();i ++){
                DataBean bean = specList.get(i);
                List<DataBean> specValues = bean.getSpecValues();
                for (DataBean specBean :specValues){
                    if (specBean.getValueId().equals(valueId)){
                        specBean.setSelect(b);
                    }
                }
            }


            StringBuffer sb = new StringBuffer();
            for (int i = 0;i < specList.size();i ++){
                DataBean bean = specList.get(i);
                List<DataBean> specValues = bean.getSpecValues();
                for (DataBean specBean :specValues){
                    if (specBean.isSelect()){
                        sb.append(bean.getValueId()).append(",");
                        LogUtils.e(bean.getValueId());
                    }
                }
            }
//            String s = sb.deleteCharAt(sb.length() - 1).toString();
//            LogUtils.e(s);
//            String value = valueIds.optString();
//            if (!StringUtils.isEmpty(value)){
//                LogUtils.e(value);
//            }
//            String sk = sku.optString(sb.toString());
//            if (!StringUtils.isEmpty(sk)){
//                LogUtils.e(sk);
//            }
        });
    }

}
