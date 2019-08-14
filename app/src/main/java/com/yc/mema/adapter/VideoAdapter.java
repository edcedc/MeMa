package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingmouren.layoutmanagergroup.CustomVideoView;
import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.view.VideoFrg;
import com.yc.mema.weight.CircleImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/29
 * Time: 20:15
 */
public class VideoAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public VideoAdapter(Context act, VideoFrg root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + bean.getAttachId(), viewHolder.iv_img);
        GlideLoadingUtils.load(act, bean.getHeadUrl(), viewHolder.iv_head);
        viewHolder.tv_name.setText(bean.getNickName());
        viewHolder.tv_moma.setText(bean.getMema());
        viewHolder.tv_content.setText(bean.getContext());
        viewHolder.tv_zan.setText(bean.getPraiseCount() + "");
        viewHolder.tv_comment.setText(bean.getDiscuss() + "");
        viewHolder.iv_follow.setBackgroundResource(bean.getfIsTrue() == 0 ? R.mipmap.tianjia_1 : R.mipmap.tianjia_1);
        viewHolder.iv_zan.setBackgroundResource(bean.getpIsTrue() == 0 ? R.mipmap.xihuan_1 : R.mipmap.xihuan_2);
        viewHolder.iv_coll.setBackgroundResource(bean.getcIsTrue() == 0 ? R.mipmap.shoucang_1 : R.mipmap.shoucan_2);
        viewHolder.video_view.setVideoPath(bean.getVideo());

        viewHolder.iv_follow.setOnClickListener(view -> {
            if (listener != null){
                listener.follow("");
            }
        });
        viewHolder.iv_zan.setOnClickListener(view -> {
            if (listener != null){
                listener.zan(bean.getVideoId(), bean.getpIsTrue());
            }
        });
        viewHolder.iv_comment.setOnClickListener(view -> {
            if (listener != null){
                listener.comment();
            }
        });
        viewHolder.iv_coll.setOnClickListener(view -> {
            if (listener != null){
                listener.collection(bean.getVideoId(), bean.getcIsTrue(), position);
            }
        });
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void collection(String id, int i, int position);
        void follow(String id);
        void comment();
        void zan(String id, int i);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_video, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatImageView iv_follow;
        AppCompatTextView tv_name;
        AppCompatTextView tv_coll;
        AppCompatTextView tv_moma;
        AppCompatTextView tv_zan;
        AppCompatTextView tv_content;
        CircleImageView iv_head;
        AppCompatTextView tv_comment;
        CustomVideoView video_view;
        AppCompatImageView iv_zan;
        AppCompatImageView iv_comment;
        AppCompatImageView iv_coll;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_moma = itemView.findViewById(R.id.tv_moma);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_coll = itemView.findViewById(R.id.tv_coll);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_zan = itemView.findViewById(R.id.tv_zan);
            iv_follow = itemView.findViewById(R.id.iv_follow);
            video_view = itemView.findViewById(R.id.video_view);
            iv_coll = itemView.findViewById(R.id.iv_coll);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            iv_zan = itemView.findViewById(R.id.iv_zan);
        }
    }

}
