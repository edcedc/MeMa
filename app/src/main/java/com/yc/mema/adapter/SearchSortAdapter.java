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
 * Date: 2019/10/9
 * Time: 17:28
 */
public class SearchSortAdapter extends BaseListViewAdapter<DataBean> {

    public SearchSortAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    private int mPosition = -1;

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_s_sort, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        viewHolder.tv_text.setText(bean.getName());
        if (position == mPosition){
            viewHolder.tv_text.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, act.getResources().getDrawable(R.mipmap.y18, null), null);
            viewHolder.tv_text.setTextColor(act.getResources().getColor(R.color.red_F67690));
        }else {
            viewHolder.tv_text.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, null, null);
            viewHolder.tv_text.setTextColor(act.getResources().getColor(R.color.black_333333));
        }
        return convertView;
    }

    class ViewHolder{

        AppCompatTextView tv_text;

        public ViewHolder(View convertView) {
            tv_text = convertView.findViewById(R.id.tv_text);
        }
    }

}
