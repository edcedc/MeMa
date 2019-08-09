package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.TimeUtil;
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

    public MessageAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        DataBean bean = listBean.get(position);
        if (holder instanceof SystemViewHolder) {
            SystemViewHolder viewHolder = (SystemViewHolder) holder;
            viewHolder.tv_title.setText(bean.getTitle());
            viewHolder.tv_content.setText(bean.getContext());
            viewHolder.iv_img.setVisibility(bean.getIsRead() == 0 ? View.VISIBLE : View.GONE);
            viewHolder.itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onClick(position, bean.getType());
                }
                UIHelper.startSystemDescFrg(root, bean);
            });
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            switch (bean.getType()){
                case 1://点赞资讯

                    break;
                case 2://评论资讯

                    break;
                case 4://回复资讯评论

                    break;
                case 8://点赞视频

                    break;
                case 16://评论视频

                    break;
                case 32://评论视频评论

                    break;
                case 64://资讯评论点赞
                    DataBean userList = bean.getUserList();
                    GlideLoadingUtils.load(act, userList.getHeadUrl(), viewHolder.iv_head, true);
                    viewHolder.tv_title.setText(userList.getNickName() +
                            " 点赞了你的评论");
                    DataBean infoDisList = bean.getInfoDisList();
                    viewHolder.tv_content.setText(infoDisList.getContext());
                    viewHolder.layout.setVisibility(View.GONE);
                    break;
                case 128://视频评论点赞

                    break;
            }
            viewHolder.tv_time.setText(TimeUtil.getTimeFormatText(bean.getCreateTime()));
            viewHolder.itemView.setOnClickListener(view -> {
                switch (bean.getType()){
                    case 1://点赞资讯

                        break;
                    case 2://评论资讯

                        break;
                    case 4://回复资讯评论

                        break;
                    case 8://点赞视频

                        break;
                    case 16://评论视频

                        break;
                    case 32://评论视频评论

                        break;
                    case 64://资讯评论点赞
                        DataBean infoDisList = bean.getInfoDisList();
                        UIHelper.startNewsDescAct(infoDisList.getInfoId(), infoDisList);
                        break;
                    case 128://视频评论点赞

                        break;
                }
            });
        }
    }

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void onClick(int position, int type);
    }

    @Override
    public int getItemViewType(int position) {
        return listBean.get(position).getType();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new SystemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_system_msg, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_msg, parent, false));
        }
    }

    class SystemViewHolder extends RecyclerView.ViewHolder {

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

    class ViewHolder extends RecyclerView.ViewHolder {

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
