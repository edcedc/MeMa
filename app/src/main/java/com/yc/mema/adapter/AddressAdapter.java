package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 14:59
 */
public class AddressAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public AddressAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        if (bean.getRegionLevel() >= 3){
            viewHolder.tv_locate.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);
        }else {
            viewHolder.tv_locate.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, act.getResources().getDrawable(R.mipmap.y16, null), null);
        }
        viewHolder.tv_locate.setText(bean.getRegionName());
        viewHolder.itemView.setOnClickListener(view -> {
            if (listener != null)listener.onClick(bean.getRegionId(), bean.getRegionName(), bean.getRegionLevel());
        });
    }

    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(String parentId, String address, int regionLevel);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_address, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_locate;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_locate = itemView.findViewById(R.id.tv_locate);
        }
    }

}
