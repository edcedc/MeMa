package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Set;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/29
 * Time: 16:42
 */
public class ShopSkuAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public ShopSkuAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_name.setText(bean.getSpecName());
        List<DataBean> list = bean.getSpecValues();
        TagAdapter<DataBean> adapter = new TagAdapter<DataBean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, DataBean bean) {
                View view = View.inflate(act, R.layout.i_sku, null);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                tvText.setText(bean.getSpecValue());
                return view;
            }

            @Override
            public void onSelected(int position, View view) {
                super.onSelected(position, view);
                DataBean bean = list.get(position);
                bean.setSelect(true);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                delegate.setStrokeColor(act.getResources().getColor(R.color.red_EC5B44));
                tvText.setTextColor(act.getResources().getColor(R.color.red_EC5B44));
                if (listener != null)listener.onClick(list, bean.getValueId(), true);
            }

            @Override
            public void unSelected(int position, View view) {
                super.unSelected(position, view);
                DataBean bean = list.get(position);
                bean.setSelect(false);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                delegate.setStrokeColor(act.getResources().getColor(R.color.black_333333));
                tvText.setTextColor(act.getResources().getColor(R.color.black_333333));
                if (listener != null)listener.onClick(list, bean.getValueId(), false);
            }
        };
        viewHolder.fl_label.setAdapter(adapter);
        for (int i = 0;i < list.size();i++){
            DataBean bean1 = list.get(i);
            if (bean1.isSelect()){
                adapter.setSelectedList(i);
            }
        }
    }

    private onClickListener listener;
    public void setListener(onClickListener listener){
        this.listener = listener;
    }
    public interface onClickListener{
        void onClick(List<DataBean> list, String valueId, boolean b);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_shop_sku, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_name;
        TagFlowLayout fl_label;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            fl_label = itemView.findViewById(R.id.fl_label);
        }
    }

}
