package com.yc.mema.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.listeners.OnAdapterClickListener;
import com.yc.mema.weight.DayCompare;

import java.text.SimpleDateFormat;
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

        viewHolder.tv_name.setText(bean.getNickName());
        viewHolder.tv_age.setText(bean.getGoDay() +
                "周岁生日");
        viewHolder.tv_time.setText(bean.getBrithday());
        int inDay = bean.getInDay();

        String[] split = bean.getBrithday().split("-");
        int year = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]);
        int day = Integer.valueOf(split[2]);

        String[] thisYear = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")).split("-");
        int tTear = Integer.valueOf(thisYear[0]);
        int tMonth = Integer.valueOf(thisYear[1]);
        int tDay = Integer.valueOf(thisYear[2]);
        if (inDay == 0){
            viewHolder.tv_content.setText("今天生日");
        }else if (month >= tMonth && day > tDay){
            viewHolder.tv_content.setText("离今年生日还有 " +
                    inDay +
                    "天");
        }else {
            viewHolder.tv_content.setText("离明年生日还有 " +
                    inDay +
                    "天");
        }
        if (mPosition == position){
            viewHolder.itemView.setBackgroundColor(act.getResources().getColor(R.color.red_F67690));
            viewHolder.tv_name.setTextColor(act.getResources().getColor(R.color.white));
            viewHolder.tv_age.setTextColor(act.getResources().getColor(R.color.white));
            viewHolder.tv_time.setTextColor(act.getResources().getColor(R.color.white));
            viewHolder.tv_content.setTextColor(act.getResources().getColor(R.color.white));
        }else {
            viewHolder.itemView.setBackgroundColor(act.getResources().getColor(R.color.white));
            viewHolder.tv_name.setTextColor(act.getResources().getColor(R.color.black_333333));
            viewHolder.tv_age.setTextColor(act.getResources().getColor(R.color.black_333333));
            viewHolder.tv_time.setTextColor(act.getResources().getColor(R.color.tab_gray));
            viewHolder.tv_content.setTextColor(act.getResources().getColor(R.color.tab_gray));
        }

        viewHolder.itemView.setOnClickListener(view -> {
            if (listener != null)listener.onClick(position);
        });
        viewHolder.itemView.setOnLongClickListener(view -> {
            if (listener != null)listener.onLongClick(position, bean.getBookId());
            return false;
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
