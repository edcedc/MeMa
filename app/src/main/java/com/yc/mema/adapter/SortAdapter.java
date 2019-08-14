package com.yc.mema.adapter;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yc.mema.R;
import com.yc.mema.base.BaseListViewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.weight.CircleImageView;

public class SortAdapter extends BaseListViewAdapter<DataBean> implements SectionIndexer {

    public static final int recordbook = 0;//记事本
    public static final int black = 1;//黑名单

    private int type;
    public SortAdapter(Context act, List listBean, int type) {
        super(act, listBean);
        this.type = type;
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_sort, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(bean.getSortLetters());
        }else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        GlideLoadingUtils.load(act, "", viewHolder.iv_head, true);

        switch (type){
            case recordbook:
                viewHolder.tvTitle.setText(bean.getNickName());
                viewHolder.tv_huifu.setVisibility(View.GONE);
                viewHolder.tv_time.setVisibility(View.VISIBLE);
                break;
            default:
                viewHolder.tv_huifu.setVisibility(View.VISIBLE);
                viewHolder.tv_time.setVisibility(View.GONE);
                break;
        }

        return convertView;
    }


     class ViewHolder{
        TextView tvLetter;
        TextView tvTitle;
        TextView tv_huifu;
        TextView tv_time;
        CircleImageView iv_head;

         public ViewHolder(View convertView) {
             tvTitle = convertView.findViewById(R.id.title);
             tvLetter = convertView.findViewById(R.id.catalog);
             iv_head = convertView.findViewById(R.id.iv_head);
             tv_huifu = convertView.findViewById(R.id.tv_huifu);
             tv_time = convertView.findViewById(R.id.tv_time);
         }
     }

    @Override
    public Object[] getSections() {
        return null;
    }
    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = listBean.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    @Override
    public int getSectionForPosition(int position) {
        return listBean.get(position).getSortLetters().charAt(0);
    }


    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }


}
