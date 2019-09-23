package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RatingBar;

import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.weight.CircleImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/29
 * Time: 15:38
 */
public class ShopCommentAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public ShopCommentAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, bean.getHeadUrl(), viewHolder.iv_head);
        viewHolder.tv_name.setText(bean.getNickName());
        viewHolder.tv_time.setText(bean.getCreateTime());
        viewHolder.tv_content.setText(bean.getContent());
        viewHolder.ratingbar.setNumStars(5);
        viewHolder.ratingbar.setRating((float) bean.getStarLevel());
        viewHolder.itemView.setOnClickListener(view -> {});
        List<DataBean> discussImgs = bean.getDiscussImgs();
        if (discussImgs != null && discussImgs.size() != 0){
            viewHolder.gridView.setVisibility(View.VISIBLE);
            GiftDescAdapter adapter = new GiftDescAdapter(act, discussImgs);
            viewHolder.gridView.setAdapter(adapter);
        }else {
            viewHolder.gridView.setVisibility(View.GONE);
        }
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_shop_comment, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_head;
        AppCompatTextView tv_name;
        AppCompatTextView tv_time;
        AppCompatTextView tv_content;
        AppCompatRatingBar ratingbar;
        GridView gridView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            ratingbar = itemView.findViewById(R.id.ratingbar);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
            gridView = itemView.findViewById(R.id.gridView);
        }
    }
}
