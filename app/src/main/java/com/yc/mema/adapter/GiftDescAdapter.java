package com.yc.mema.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.base.BaseListViewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.weight.PictureSelectorTool;
import com.yc.mema.weight.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 21:05
 */
public class GiftDescAdapter extends BaseListViewAdapter<DataBean> {
    public GiftDescAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected View getCreateVieww(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(act, R.layout.i_gift_img, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getAttachId(), viewHolder.iv_img);
        convertView.setOnClickListener(view -> {
            List<LocalMedia> list = new ArrayList<>();
            for (DataBean dataBean : listBean){
                LocalMedia media = new LocalMedia();
                media.setPath(CloudApi.SERVLET_IMG_URL + dataBean.getAttachId());
                list.add(media);
            }
            PictureSelectorTool.PictureMediaType((Activity) act, list, position);
        });

        return convertView;
    }

    class ViewHolder{

        RoundImageView iv_img;

        public ViewHolder(View convertView) {
            iv_img = convertView.findViewById(R.id.iv_img);
        }
    }

}
