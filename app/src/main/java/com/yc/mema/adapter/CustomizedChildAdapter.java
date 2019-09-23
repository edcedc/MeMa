package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseListViewAdapter;
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
 * Time: 20:00
 */
public class CustomizedChildAdapter extends BaseListViewAdapter<DataBean> {
    public CustomizedChildAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_custom, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        List<DataBean> welfareImgs = bean.getWelfareImgs();
        if (welfareImgs != null && welfareImgs.size() != 0){
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + welfareImgs.get(0).getAttachId(), viewHolder.iv_img);
        }
        viewHolder.tv_title.setText(bean.getWalTitle());
        viewHolder.tv_content.setText("100" +
                "元通用代金券");
        viewHolder.tv_location.setText("12" +
                "km");
        viewHolder.ratingbar.setRating((float) 2);
        viewHolder.itemView.setOnClickListener(view -> UIHelper.startCustomizedDescAct(bean));
        return convertView;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_location;
        AppCompatTextView tv_content;
        AppCompatTextView tv_hui;
        AppCompatRatingBar ratingbar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_content = itemView.findViewById(R.id.tv_content);
            ratingbar = itemView.findViewById(R.id.ratingbar);
            tv_hui = itemView.findViewById(R.id.tv_hui);
        }
    }

}
