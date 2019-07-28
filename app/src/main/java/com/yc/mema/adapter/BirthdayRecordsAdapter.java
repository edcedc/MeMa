package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.listeners.OnAdapterClickListener;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/26
 * Time: 15:41
 */
public class BirthdayRecordsAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public BirthdayRecordsAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        viewHolder.tv_name.setText("妈妈");
        viewHolder.tv_age.setText("60周岁生日");
        viewHolder.tv_time.setText("1965-10-07");
        viewHolder.tv_content.setText("离下一次生日还有 365天");

        if (mPosition == position){
            viewHolder.itemView.setBackgroundColor(act.getColor(R.color.red_F67690));
            viewHolder.tv_name.setTextColor(act.getColor(R.color.white));
            viewHolder.tv_age.setTextColor(act.getColor(R.color.white));
            viewHolder.tv_time.setTextColor(act.getColor(R.color.white));
            viewHolder.tv_content.setTextColor(act.getColor(R.color.white));
        }else {
            viewHolder.itemView.setBackgroundColor(act.getColor(R.color.white));
            viewHolder.tv_name.setTextColor(act.getColor(R.color.black_333333));
            viewHolder.tv_age.setTextColor(act.getColor(R.color.black_333333));
            viewHolder.tv_time.setTextColor(act.getColor(R.color.tab_gray));
            viewHolder.tv_content.setTextColor(act.getColor(R.color.tab_gray));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)listener.onClick(position);
            }
        });
    }

    private int mPosition = -1;

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public OnAdapterClickListener listener;
    public void setOnAdapterClickListener(OnAdapterClickListener onMonthChangeListener){
        this.listener = onMonthChangeListener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_birthday, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_name;
        AppCompatTextView tv_age;
        AppCompatTextView tv_time;
        AppCompatTextView tv_content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

}
