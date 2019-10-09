package com.yc.mema.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.weight.RoundImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/26
 * Time: 20:31
 */
public class ShopAdapter extends BaseRecyclerviewAdapter<DataBean> {
    public ShopAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        List<DataBean> imgs = bean.getGoodSpuImgs();
        if (imgs != null && imgs.size() != 0){
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + imgs.get(0).getAttachId(), viewHolder.iv_img);
        }
        viewHolder.tv_title.setText(bean.getGoodName());

        double price = bean.getPrice();
        String sales = bean.getSales() + "";
        SpannableString hText = new SpannableString("¥" + price + "    已售" + sales + "件");
        hText.setSpan(new ForegroundColorSpan(Color.parseColor("#EC5B44")), 0, String.valueOf(price).length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        hText.setSpan(new AbsoluteSizeSpan(10, true), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        hText.setSpan(new AbsoluteSizeSpan(10, true), hText.length() - (1 + sales.length() + 5), hText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.tv_content.setText(hText);

        viewHolder.itemView.setOnClickListener(view -> UIHelper.startShopDescAct(bean.getGoodId()));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_shop, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title;
        AppCompatTextView tv_content;
        RoundImageView iv_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

}
