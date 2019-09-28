package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.GlideLoadingUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/11
 * Time: 18:42
 */
public class TeaAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public TeaAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getAttachId(), viewHolder.iv_img);

        switch (bean.getType()){
            case 1:
                viewHolder.tv_title.setText(bean.getWalTitle());
                viewHolder.tv_admission.setText(bean.getDiscount());
                viewHolder.tv_admission.setVisibility(View.VISIBLE);
                SpannableString spannableString = new SpannableString("￥" + bean.getPrice());
                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                viewHolder.tv_price.setText(spannableString);
                break;
            case 2:
                viewHolder.tv_title.setText(bean.getCategoryName());
                viewHolder.tv_price.setText(bean.getPrice() + "元起");
                viewHolder.tv_admission.setVisibility(View.GONE);
                break;
        }
        viewHolder.itemView.setOnClickListener(view -> {
            switch (bean.getType()){
                case 1:
                    UIHelper.startGiftAct(bean.getWelId());
                    break;
                case 2:
                    UIHelper.startShopDescAct(bean.getGoodId());
                    break;
            }
        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_tea, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_admission;
        AppCompatTextView tv_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_admission = itemView.findViewById(R.id.tv_admission);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }


}
