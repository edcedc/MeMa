package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseListViewAdapter;
import com.yc.mema.bean.DataBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/27
 * Time: 16:25
 */
public class ShopCategoryLeftAdapter extends BaseListViewAdapter<DataBean> {

    public ShopCategoryLeftAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_shop_cat_left, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        viewHolder.tv_name.setText(bean.getClassifyTitle());
        if (mPosition == position){
            viewHolder.view.setVisibility(View.VISIBLE);
        }else {
            viewHolder.view.setVisibility(View.GONE);
        }
        return convertView;
    }

    private int mPosition = -1;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    class ViewHolder{

        AppCompatTextView tv_name;
        View view;

        public ViewHolder(View convertView) {
            tv_name = convertView.findViewById(R.id.tv_name);
            view = convertView.findViewById(R.id.view);
        }
    }

}
