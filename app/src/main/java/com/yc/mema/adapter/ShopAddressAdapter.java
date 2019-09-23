package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
 * Date: 2019/9/9
 * Time: 16:34
 */
public class ShopAddressAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public ShopAddressAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_title.setText(bean.getUserName());
        viewHolder.tv_phone.setText(bean.getIphone());
        viewHolder.tv_desc.setText(bean.getCounties() + bean.getAddress());
        if (bean.getStatus() == 2){
            viewHolder.tv_moren.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.shezhi1, null),
                    null, null, null);
            viewHolder.tv_moren.setTextColor(act.getResources().getColor(R.color.red_EC5B44));
        }else {
            viewHolder.tv_moren.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.xuanzhe1, null),
                    null, null, null);
            viewHolder.tv_moren.setTextColor(act.getResources().getColor(R.color.black_333333));
        }
        viewHolder.tv_moren.setOnClickListener(view -> listener.onMoRen(bean.getAddressId(), position));
        viewHolder.tv_edit.setOnClickListener(view -> listener.onEdit(bean.getAddressId(), position, bean));
        viewHolder.tv_del.setOnClickListener(view -> listener.onDel(bean.getAddressId(), position));
    }

    public OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onEdit(String id, int position, DataBean bean);
        void onDel(String id, int position);
        void onMoRen(String id, int position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_shop_address, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title;
        AppCompatTextView tv_phone;
        AppCompatTextView tv_desc;
        AppCompatTextView tv_moren;
        AppCompatTextView tv_edit;
        AppCompatTextView tv_del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_moren = itemView.findViewById(R.id.tv_moren);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_del = itemView.findViewById(R.id.tv_del);
        }
    }

}
