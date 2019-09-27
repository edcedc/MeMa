package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
 * Date: 2019/8/26
 * Time: 20:31
 */
public class ShopCategoryRightAdapter extends BaseRecyclerviewAdapter<DataBean> {
    public ShopCategoryRightAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    private int mPosition = -1;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_title.setText(bean.getClassifyTitle());

        if (mPosition == position){
            viewHolder.tv_title.setTextColor(act.getResources().getColor(R.color.red_EC5B44));
            viewHolder.tv_title.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, act.getResources().getDrawable(R.mipmap.list_check, null), null);
        }else {
            viewHolder.tv_title.setTextColor(act.getResources().getColor(R.color.black_333333));
            viewHolder.tv_title.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, null, null);
        }

        viewHolder.itemView.setOnClickListener(view -> listener.onClick(position, bean.getClassifyId(), bean.getClassifyTitle()));
    }

    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(int position, String classifyId, String classifyTitle);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_category_shop_right, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

}
