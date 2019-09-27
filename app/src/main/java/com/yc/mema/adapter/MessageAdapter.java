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
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.TimeUtil;
import com.yc.mema.view.VideoFrg;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.weight.CircleImageView;
import com.yc.mema.weight.RoundImageView;

import java.util.ArrayList;
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
                    DataBean userList4 = bean.getUserList();
                    GlideLoadingUtils.load(act, userList4.getHeadUrl(), viewHolder.iv_head, true);
                    DataBean binfoDisList4 = bean.getbInfoDisList();
                    viewHolder.tv_content.setText(binfoDisList4.getContext());
                    DataBean infoDisList4 = bean.getInfoDisList();
                    viewHolder.layout.setVisibility(View.VISIBLE);
                    viewHolder.iv_img1.setVisibility(View.GONE);
                    viewHolder.tv_comment.setText(infoDisList4.getContext());
                    viewHolder.tv_title.setText(infoDisList4.getNickName() +
                            " 回复了你的评论");
                    break;
                case 8://点赞视频
                    DataBean userList = bean.getUserList();
                    GlideLoadingUtils.load(act, userList.getHeadUrl(), viewHolder.iv_head, true);
                    viewHolder.tv_title.setText(userList.getNickName() +
                            " 点赞了你的视频");
                    viewHolder.layout.setVisibility(View.GONE);
                    DataBean infoList8 = bean.getInfoList();
                    viewHolder.tv_content.setText(infoList8.getContext());
                    break;
                case 16://评论视频
                    DataBean userList16 = bean.getUserList();
                    GlideLoadingUtils.load(act, userList16.getHeadUrl(), viewHolder.iv_head, true);
                    viewHolder.tv_title.setText(userList16.getNickName() +
                            " 评论了你的视频");
                    viewHolder.layout.setVisibility(View.VISIBLE);
                    DataBean infoDisList16 = bean.getInfoDisList();
                    viewHolder.tv_content.setText(infoDisList16.getContext());
                    DataBean infoList16 = bean.getInfoList();
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + infoList16.getAttachId(), viewHolder.iv_img1);
                    viewHolder.tv_comment.setText(infoList16.getContext());
                    break;
                case 32://评论视频评论
                    DataBean userList32 = bean.getUserList();
                    GlideLoadingUtils.load(act, userList32.getHeadUrl(), viewHolder.iv_head, true);
                    viewHolder.tv_title.setText(userList32.getNickName() +
                            " 回复了你视频的评论");
                    viewHolder.layout.setVisibility(View.VISIBLE);
                    viewHolder.iv_img1.setVisibility(View.GONE);
                    DataBean infoDisList32 = bean.getInfoDisList();
                    viewHolder.tv_content.setText(infoDisList32.getContext());
                    viewHolder.tv_comment.setText(infoDisList32.getContext());
                    break;
                case 64://资讯评论点赞
                    DataBean userList64 = bean.getUserList();
                    GlideLoadingUtils.load(act, userList64.getHeadUrl(), viewHolder.iv_head, true);
                    viewHolder.tv_title.setText(userList64.getNickName() +
                            " 点赞了你的评论");
                    DataBean infoDisList = bean.getbInfoDisList();
                    viewHolder.tv_content.setText(infoDisList.getContext());
                    viewHolder.layout.setVisibility(View.GONE);
                    break;
                case 128://视频评论点赞
                    DataBean userList128 = bean.getUserList();
                    GlideLoadingUtils.load(act, userList128.getHeadUrl(), viewHolder.iv_head, true);
                    viewHolder.tv_title.setText(userList128.getNickName() +
                            " 点赞了你的视频评论");
                    viewHolder.layout.setVisibility(View.GONE);
                    viewHolder.iv_img1.setVisibility(View.GONE);
                    DataBean infoList128 = bean.getInfoList();
                    viewHolder.tv_content.setText(infoList128.getContext());
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
                        DataBean infoDisList4 = bean.getbInfoDisList();
                        UIHelper.startNewsDescAct(infoDisList4.getInfoId(), infoDisList4);
                        break;
                    case 8://点赞视频
                    case 16://评论视频
                    case 32://评论视频评论
                    case 128://视频评论点赞
                        DataBean infoList = bean.getInfoList();
                        List<DataBean> list = new ArrayList<>();
                        list.add(infoList);
                        UIHelper.startVideoAct(VideoFrg.MSG_VIDEO, list, 0);
                        break;
                    case 64://资讯评论点赞
                        DataBean infoDisList = bean.getbInfoDisList();
                        UIHelper.startNewsDescAct(infoDisList.getInfoId(), infoDisList);
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
