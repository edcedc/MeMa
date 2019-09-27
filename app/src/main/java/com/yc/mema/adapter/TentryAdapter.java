package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
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
 * Date: 2019/9/27
 * Time: 16:28
 */
public class TentryAdapter extends BaseListViewAdapter<DataBean> {

    public TentryAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_tentry, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);

        viewHolder.img.setBackgroundResource(bean.getImg());
        viewHolder.tv_text.setText((position + 1) + "、" + bean.getTitle());

        return convertView;
    }

    class ViewHolder{

        AppCompatImageView img;
        AppCompatTextView tv_text;

        public ViewHolder(View convertView) {
            img = convertView.findViewById(R.id.iv_img);
            tv_text = convertView.findViewById(R.id.tv_text);

        }
    }

}
