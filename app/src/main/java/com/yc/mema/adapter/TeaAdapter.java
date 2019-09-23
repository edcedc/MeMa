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
import com.yc.mema.utils.GlideLoadingUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/11
 * Time: 18:42
 */
public class TeaAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public TeaAdapter(Context act, List<DataBean> listBean, int type) {
        super(act, listBean);
        this.type = type;
    }

    private int type;

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        viewHolder.itemView.setOnClickListener(view -> {});

        if (type == 0){
            viewHolder.tv_title.setText("花园酒店双人悠然下午茶");
            GlideLoadingUtils.load(act, "https://wx1.sinaimg.cn/mw690/78a9167dgy1g6vrtg59y5j209606o0w4.jpg", viewHolder.iv_img);
            viewHolder.tv_admission.setVisibility(View.VISIBLE);
            SpannableString spannableString = new SpannableString("￥" +
                    "168");
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            viewHolder.tv_price.setText(spannableString);
        }else {
            viewHolder.tv_title.setText("蓝莓爱上慕斯蛋糕");
            GlideLoadingUtils.load(act, "https://wx2.sinaimg.cn/mw690/78a9167dgy1g6vqt1xdmsj209606oq6x.jpg", viewHolder.iv_img);
            viewHolder.tv_admission.setVisibility(View.GONE);
            viewHolder.tv_price.setText("99" +
                    "元起");
        }
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
