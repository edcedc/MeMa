package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.blankj.utilcode.util.LogUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
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

        GlideLoadingUtils.load(act, "", viewHolder.iv_img);
        GlideLoadingUtils.load(act, "", viewHolder.iv_head);
        viewHolder.tv_name.setText("@Z繁浩");
        viewHolder.tv_moma.setText("M-19951215ae4f");
        viewHolder.tv_content.setText("也许不是每个人都能在城市生活中找到安宁，便不怪乎他要逆时光之流而行于是我逃离到了西藏。去纳木错看满天星，去阿里徒步岗仁波齐寻找");

        viewHolder.fy_layout.setOnClickListener(view -> {
            if (bean.isPlay()){
                bean.setPlay(false);
            }else {
                bean.setPlay(true);
            }
            if (listener != null){
                listener.play(bean.isPlay(), position);
            }
        });
        viewHolder.cb_follow.setOnClickListener(view -> {
            if (listener != null){
                listener.follow("");
            }
        });
        viewHolder.cb_zan.setOnClickListener(view -> {
            if (listener != null){
                listener.zan("");
            }
        });
        viewHolder.tv_comment.setOnClickListener(view -> {
            if (listener != null){
                listener.comment();
            }
        });
        viewHolder.cb_coll.setOnClickListener(view -> {
            if (listener != null){
                listener.collection("");
            }
        });
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void play(boolean isPlay, int position);
        void collection(String id);
        void follow(String id);
        void comment();
        void zan(String id);
    }


    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_video, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        View fy_layout;
        AppCompatTextView tv_name;
        AppCompatTextView tv_moma;
        AppCompatTextView tv_content;
        CircleImageView iv_head;
        CheckBox cb_coll;
        AppCompatTextView tv_comment;
        CheckBox cb_zan;
        CheckBox cb_follow;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            fy_layout = itemView.findViewById(R.id.fy_layout);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_moma = itemView.findViewById(R.id.tv_moma);
            tv_content = itemView.findViewById(R.id.tv_content);
            iv_head = itemView.findViewById(R.id.iv_head);
            cb_coll = itemView.findViewById(R.id.cb_coll);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            cb_zan = itemView.findViewById(R.id.cb_zan);
            cb_follow = itemView.findViewById(R.id.cb_follow);
        }
    }

}
