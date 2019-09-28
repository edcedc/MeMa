package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseListViewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 16:56
 */
public class TentryHelpAdapter extends BaseListViewAdapter<DataBean> {

    public TentryHelpAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_tentry_help, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        viewHolder.tv_text.setText(bean.getTitle());
        convertView.setOnClickListener(view -> UIHelper.startHtmlAct(bean.getTitle(), bean.getContext()));
        return convertView;
    }

    class ViewHolder{

        AppCompatTextView tv_text;

        public ViewHolder(View convertView) {
            tv_text = convertView.findViewById(R.id.tv_text);
        }
    }

}
