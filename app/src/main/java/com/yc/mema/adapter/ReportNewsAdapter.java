package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 22:31
 */
public class ReportNewsAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public ReportNewsAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    private int mPosition = -1;

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_locate.setText(bean.getSoName());
        viewHolder.iv_img.setVisibility(mPosition == position ? View.VISIBLE : View.GONE);
        viewHolder.itemView.setOnClickListener(view -> {
            if (listener != null){
                listener.onClick(position);
            }
        });
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onClick(int position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_report, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_locate;
        AppCompatImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_locate = itemView.findViewById(R.id.tv_locate);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }


}
