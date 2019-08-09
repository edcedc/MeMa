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
import com.yc.mema.controller.UIHelper;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 17:06
 */
public class ComplaintAdapter extends BaseRecyclerviewAdapter<DataBean> {

    private String id;
    private int type;

    public ComplaintAdapter(Context act, BaseFragment root, List<DataBean> listBean, String id, int type) {
        super(act, root, listBean);
        this.id = id;
        this.type = type;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_locate.setCompoundDrawablesWithIntrinsicBounds(null,
                null, act.getResources().getDrawable(R.mipmap.y16, null), null);
        viewHolder.tv_locate.setText(bean.getSoName());
        viewHolder.itemView.setOnClickListener(view -> {
            UIHelper.startReportFrg(root, id, type, bean.getSoId(), bean.getSoName());
        });
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
