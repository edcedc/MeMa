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

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.yc.mema.R;
import com.yc.mema.adapter.ShopSkuAdapter;
import com.yc.mema.base.BaseBottomSheetFrg;
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
public class ShopSkuBottonFrg extends BaseBottomSheetFrg implements View.OnClickListener {

    private AppCompatImageView ivImg;
    private AppCompatTextView tvPrice;
    private AppCompatTextView tvSales;
    private AppCompatTextView tvSku;
    private AppCompatTextView tvNum;
    private DataBean bean;

    private ShopSkuAdapter adapter;
    private JSONObject valueIds;
    private JSONObject sku;
    private String skuId;
    private String getSkuId;//库存ID
    private List<DataBean> specList;
    private StringBuffer sbName;
    private String skNum;
    private double price;

    @Override
    public void onClick(View view) {
        int num = Integer.valueOf(tvNum.getText().toString());
        switch (view.getId()){
            case R.id.bt_submit:
                if (num > Integer.valueOf(skNum)){
                    return;
                }
                if (listener != null){
                    listener.OnClick(sbName.toString(), num, price, getSkuId);
                    dismiss();
                }
                break;
            case R.id.fy_close:
                dismiss();
                break;
            case R.id.tv_jia:
                num += 1;
                tvNum.setText(num + "");
                break;
            case R.id.tv_jian:
                if (num == 0){
                    return;
                }
                num -= 1;
                tvNum.setText(num + "");
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

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void OnClick(String sku, int num, double price, String skuId);
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.bt_submit).setOnClickListener(this);
        ivImg = view.findViewById(R.id.iv_img);
        tvPrice = view.findViewById(R.id.tv_price);
        tvSales = view.findViewById(R.id.tv_sales);
        tvSku = view.findViewById(R.id.tv_sku);
        view.findViewById(R.id.tv_jia).setOnClickListener(this);
        tvNum = view.findViewById(R.id.tv_num);
        view.findViewById(R.id.tv_jian).setOnClickListener(this);
        view.findViewById(R.id.fy_close).setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<DataBean> imgs = bean.getGoodSpuImgs();
        if (imgs != null && imgs.size() != 0){
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + imgs.get(0).getAttachId(), ivImg);
        }
        price = bean.getPrice();
        SpannableString hText = new SpannableString("¥" + price);
        hText.setSpan(new AbsoluteSizeSpan(9, true), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvPrice.setText(hText);

        specList = bean.getSpecList();
        if (sbName != null){
            String[] split = sbName.toString().split("、");
            for (int i = 0;i < specList.size();i ++){
                DataBean bean = specList.get(i);
                List<DataBean> specValues = bean.getSpecValues();
                for (DataBean specBean :specValues){
                    for (String s : split){
                        if (specBean.getSpecValue().equals(s)){
                            specBean.setSelect(true);
                        }
                    }
                }
            }
        }
        adapter = new ShopSkuAdapter(act, specList);
        recyclerView.setLayoutManager(new LinearLayoutManager(act));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        recyclerView.setAdapter(adapter);

        try {
            JSONObject object = new JSONObject(bean.getSpecSku());
            valueIds = object.optJSONObject("valueIds");
            this.sku = object.optJSONObject("sku");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.setListener((list, valueId, b) -> {
            sbName = new StringBuffer();
            StringBuffer sbId = new StringBuffer();
            for (int i = 0; i < specList.size(); i ++){
                DataBean bean = specList.get(i);
                List<DataBean> specValues = bean.getSpecValues();
                for (DataBean specBean :specValues){
                    if (specBean.isSelect()){
                        sbName.append(specBean.getSpecValue()).append("、");
                        sbId.append(specBean.getValueId()).append(",");
                    }
                }
            }
            int length = sbName.length();
            if (length != 0){
                tvSku.setText("已选：" + sbName.deleteCharAt(length - 1).toString());
            }else {
                tvSku.setText("已选：");
            }
            int length1 = sbId.length();
            if (length1 == 0){
                tvSales.setText("库存有0件");
                return;
            }
            String id = sbId.deleteCharAt(length1 - 1).toString();
            getSkuId = id;
            String value = valueIds.optString(id);
            if (!StringUtils.isEmpty(value)){
                tvPrice.setText("");
                skuId = value;
                this.price = Double.valueOf(skuId);
                SpannableString text = new SpannableString("¥" + skuId);
                text.setSpan(new AbsoluteSizeSpan(9, true), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tvPrice.setText(text);
            }else {
                skuId = null;
                this.price = bean.getPrice();
            }
            skNum = this.sku.optString(id);
            if (!StringUtils.isEmpty(skNum)){
                tvSales.setText("库存" + skNum + "件");
            }else {
                tvSales.setText("库存有0件");
            }
        });
    }

}
