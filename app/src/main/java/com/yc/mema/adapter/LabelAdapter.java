package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseListViewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.weight.CircleImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 18:16
 */
public class LabelAdapter extends BaseListViewAdapter<DataBean> {
    public LabelAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_label, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        viewHolder.iv_img.setBackground(act.getDrawable(bean.getImg()));
        viewHolder.tv_title.setText(bean.getTitle());
        return convertView;
    }

    class ViewHolder{

        CircleImageView iv_img;
        AppCompatTextView tv_title;

        public ViewHolder(View convertView) {
            iv_img = convertView.findViewById(R.id.iv_img);
            tv_title = convertView.findViewById(R.id.tv_title);
        }
    }

}
