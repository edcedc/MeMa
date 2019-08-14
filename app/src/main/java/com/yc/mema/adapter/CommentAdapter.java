package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.flyco.roundview.RoundLinearLayout;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.Constants;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.weight.CircleImageView;
import com.yc.mema.weight.ClipboardUtils;
import com.yc.mema.weight.WithScrollListView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 23:56
 */
public class CommentAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public CommentAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }

    private int type = 0;//0 咨询评论  1 视频评论
    public CommentAdapter(Context act, BaseFragment root, List<DataBean> listBean, int type) {
        super(act, root, listBean);
        this.type = type;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);

        GlideLoadingUtils.load(act, bean.getHeadUrl(), viewHolder.iv_head);
        viewHolder.tv_name.setText(bean.getNickName());
        viewHolder.tv_content.setText(bean.getContext());
        viewHolder.tv_time.setText(bean.getCreateTime());
        viewHolder.tv_zan.setText(bean.getPraiseCount() + "");

        final List<DataBean> list = bean.getList();
        if (list != null && list.size() != 0){
            viewHolder.layout.setVisibility(View.VISIBLE);
            final CommentChildAdapter adapter = new CommentChildAdapter(act, list);
            viewHolder.listView.setAdapter(adapter);
            viewHolder.listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
                if (type == 0){
                    //View代表方法传入的控件
                    int[] viewLocation = new int[2];
                    view.getLocationInWindow(viewLocation);
                    int viewX = viewLocation[0]; // x 坐标
                    int viewY = viewLocation[1]; // y 坐标
                    PopupWindowTool.clickXY(view);
                    PopupWindowTool.createPopupWindow(act, view, new PopupWindowTool.OnGestureClickListener() {
                        @Override
                        public void copy() {
                            // 得到剪贴板管理器
                            ClipboardUtils.copyText(list.get(i).getContext());
                            showToast("复制成功");
                        }

                        @Override
                        public void report() {
                            UIHelper.startReportNewsFrg(root, list.get(i).getDiscussId(), list.get(i).getInfoId(), Constants.COMMENT_CAUSES_COMPLAINTS);
                        }
                    });
                }

                return false;
            });
            viewHolder.listView.setOnItemClickListener((adapterView, view, i, l) -> {
                if (listener != null){
                    listener.onSecondComment(position, bean.getInfoId(), bean.getDiscussId(), bean.getUserId());
                }
            });
            viewHolder.tv_lock.setOnClickListener(view -> {
                if (!adapter.isLock()){
                    adapter.setLock(true);
                    viewHolder.tv_lock.setText("收起回复>");
                }else {
                    adapter.setLock(false);
                    viewHolder.tv_lock.setText("展开" +
                            (list.size() - CommentChildAdapter.lockNum) +
                            "条回复>");
                }
                adapter.notifyDataSetChanged();
            });
            if (list.size() > CommentChildAdapter.lockNum){
                viewHolder.tv_lock.setVisibility(View.VISIBLE);
                viewHolder.tv_lock.setText("展开" +
                        (list.size() - CommentChildAdapter.lockNum) +
                        "条回复>");
            }else {
                viewHolder.tv_lock.setVisibility(View.GONE);
                viewHolder.tv_lock.setText("收起回复>");
            }
        }else {
            viewHolder.layout.setVisibility(View.GONE);
        }
        if (bean.getIsTrue() == 0){
            viewHolder.tv_zan.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y34, null),
                    null, null, null);
        }else {
            viewHolder.tv_zan.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y45, null),
                    null, null, null);
        }

        viewHolder.itemView.setOnClickListener(view -> {
            if (listener != null){
                listener.onSecondComment(position, type == 0 ? bean.getInfoId() : bean.getVideoId(), bean.getDiscussId(), null);
            }
        });
        viewHolder.tv_zan.setOnClickListener(view -> {
            if (listener != null){
                listener.onZan(position, bean.getDiscussId(), bean.getIsTrue());
            }
        });
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onSecondComment(int position, String infoId, String discussId, String pUserId);
        void onZan(int position, String discussId, int type);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_comment, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView iv_head;
        AppCompatTextView tv_name;
        AppCompatTextView tv_content;
        AppCompatTextView tv_time;
        AppCompatTextView tv_zan;
        AppCompatTextView tv_lock;
        WithScrollListView listView;
        RoundLinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            listView = itemView.findViewById(R.id.listView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_zan = itemView.findViewById(R.id.tv_zan);
            tv_lock = itemView.findViewById(R.id.tv_lock);
            layout = itemView.findViewById(R.id.layout);
        }
    }


}
