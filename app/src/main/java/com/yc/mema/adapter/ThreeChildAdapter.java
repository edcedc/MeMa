package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.weight.RoundImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 21:43
 */
public class ThreeChildAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public ThreeChildAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        viewHolder.tv_title.setText("首届世界人道主义峰会在土耳其闭幕，与会方共作出 1500项承诺");
        viewHolder.tv_time.setText("2019-05-24");
        GlideLoadingUtils.load(act, "", viewHolder.iv_img);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_three_child_first, parent, false));
        }else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_three_child, parent, false));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title;
        AppCompatTextView tv_time;
        RoundImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

}