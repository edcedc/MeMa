package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.weight.CircleImageView;
import com.yc.mema.weight.RoundImageView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/31
 * Time: 17:49
 */
public class MessageAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public MessageAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        DataBean bean = listBean.get(position);
        if (holder instanceof SystemViewHolder){
            SystemViewHolder viewHolder = (SystemViewHolder) holder;
            viewHolder.tv_title.setText("系统通知");
            viewHolder.tv_content.setText("群聊APP5.4版本上线啦，欢迎大家下载玩耍");
            viewHolder.itemView.setOnClickListener(view ->
                    UIHelper.startHtmlAct(HtmlAct.SYSTEM)
            );
        }else {
            ViewHolder viewHolder = (ViewHolder) holder;
            GlideLoadingUtils.load(act, "", viewHolder.iv_head);
            viewHolder.tv_title.setText("小妹妹 " +
                    "回复了" +
                    "你");
            viewHolder.tv_content.setText("爱上西藏或者有勇气前来，真的好喜欢西藏。");
            viewHolder.tv_time.setText("3小时");
            GlideLoadingUtils.load(act, "", viewHolder.iv_img1);
            viewHolder.tv_comment.setText("也许不是每个人都能在城市生活中找到安宁， 便不怪乎他要逆时光之流而行...");
            viewHolder.itemView.setOnClickListener(view -> {} );
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : position;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new SystemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_system_msg, parent, false));
        }else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_msg, parent, false));
        }
    }

    class SystemViewHolder  extends RecyclerView.ViewHolder{

        CircleImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_content;

        public SystemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView iv_head;
        CircleImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_content;
        AppCompatTextView tv_time;
        AppCompatTextView tv_comment;
        View layout;
        RoundImageView iv_img1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            layout = itemView.findViewById(R.id.layout);
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            tv_comment = itemView.findViewById(R.id.tv_comment);
        }
    }

}
