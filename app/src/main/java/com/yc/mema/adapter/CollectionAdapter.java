package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.weight.CircleImageView;
import com.yc.mema.weight.RoundImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 16:21
 */
public class CollectionAdapter extends BaseRecyclerviewAdapter<DataBean> {

    private int type;
    private boolean isState = false;
    private boolean isMe = false;

    public CollectionAdapter(Context act, List<DataBean> listBean, int type) {
        super(act, listBean);
        this.type = type;
    }

    public CollectionAdapter(Context act, List<DataBean> listBean, int type, boolean isState) {
        super(act, listBean);
        this.type = type;
        this.isState = isState;
    }

    public CollectionAdapter(Context act, List<DataBean> listBean, int type, boolean isState, boolean isMe) {
        super(act, listBean);
        this.type = type;
        this.isState = isState;
        this.isMe = isMe;
    }

    private boolean isDel = false;

    public void setDel(boolean del) {
        isDel = del;
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        final DataBean bean = listBean.get(position);
        if (holder instanceof ProneViewHolder){
            ProneViewHolder viewHolder = (ProneViewHolder) holder;
            if (isMe){
                viewHolder.layout.setVisibility(View.GONE);
            }
            viewHolder.tv_name.setText("番号");
            isDel(viewHolder.cb_submit);
            viewHolder.cb_submit.setChecked(bean.isSelect());
            viewHolder.cb_submit.setOnClickListener(view -> {
                if (listener != null) {
                    if (bean.isSelect()){
                        bean.setSelect(false);
                    }else {
                        bean.setSelect(true);
                    }
                    viewHolder.cb_submit.setChecked(bean.isSelect());
                    listener.click(position, bean.isSelect());
                }
            });
            viewHolder.itemView.setOnClickListener(view -> UIHelper.startVideoAct(isState));
        }else if (holder instanceof ConsultViewHolder){
            ConsultViewHolder viewHolder = (ConsultViewHolder) holder;
            viewHolder.tv_title.setText(bean.getTitle());
            viewHolder.tv_time.setText(bean.getCreateTime().split(" ")[0]);
            isDel(viewHolder.cb_submit);
            viewHolder.cb_submit.setChecked(bean.isSelect());
            viewHolder.cb_submit.setOnClickListener(view -> {
                if (listener != null) {
                    if (bean.isSelect()){
                        bean.setSelect(false);
                    }else {
                        bean.setSelect(true);
                    }
                    viewHolder.cb_submit.setChecked(bean.isSelect());
                    listener.click(position, bean.isSelect());
                }
            });
            viewHolder.itemView.setOnClickListener(view -> UIHelper.startNewsDescAct(bean.getInfoId(), null));
            List<DataBean> img = bean.getInformationImg();
            if (img != null && img.size() != 0){
                GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + img.get(0).getAttachId(), viewHolder.iv_img);
            }
        }else if (holder instanceof GiftViewHolder){
            GiftViewHolder viewHolder = (GiftViewHolder) holder;
            viewHolder.tv_title.setText(bean.getWalTitle());
            viewHolder.tv_add.setText(bean.getPcyAdd() + bean.getAddress());
            String discount = bean.getDiscount();
            if (!StringUtils.isEmpty(discount)){
                String[] split = discount.split("，");
                viewHolder.fl_label.removeAllViews();
                viewHolder.fl_label.setAdapter(new TagAdapter<String>(split){
                    @Override
                    public View getView(FlowLayout parent, int position, String dataBean) {
                        View view = View.inflate(act, R.layout.i_gift_label, null);
                        TextView tvText = view.findViewById(R.id.tv_text);
                        tvText.setText(dataBean);
                        return view;
                    }
                });
            }

            List<DataBean> welfareImgs = bean.getWelfareImgs();
            switch (welfareImgs.size()){
                case 0:
                    viewHolder.iv_img.setVisibility(View.VISIBLE);
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + welfareImgs.get(0).getAttachId(), viewHolder.iv_img);
                    break;
                case 1:
                    viewHolder.iv_img.setVisibility(View.VISIBLE);
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + welfareImgs.get(0).getAttachId(), viewHolder.iv_img);
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + welfareImgs.get(1).getAttachId(), viewHolder.iv_img1);
                    break;
                default:
                    viewHolder.iv_img.setVisibility(View.VISIBLE);
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + welfareImgs.get(0).getAttachId(), viewHolder.iv_img);
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + welfareImgs.get(1).getAttachId(), viewHolder.iv_img1);
                     viewHolder.iv_img2.setVisibility(View.VISIBLE);
                    GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + welfareImgs.get(2).getAttachId(), viewHolder.iv_img2);
                    break;
            }

            isDel(viewHolder.cb_submit);
            viewHolder.cb_submit.setChecked(bean.isSelect());
            viewHolder.cb_submit.setOnClickListener(view -> {
                if (listener != null) {
                    if (bean.isSelect()){
                        bean.setSelect(false);
                    }else {
                        bean.setSelect(true);
                    }
                    viewHolder.cb_submit.setChecked(bean.isSelect());
                    listener.click(position, bean.isSelect());
                }
            });
            viewHolder.itemView.setOnClickListener(view -> UIHelper.startGiftAct(bean.getWelId()));
        }
    }

    private OnClickListener listener;
    public interface OnClickListener{
        void click(int position, boolean isSelect);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }


    private void isDel(CheckBox box){
        box.setVisibility(isDel ? View.VISIBLE : View.GONE);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        switch (type){
            case 0:
                return new ProneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_prone, parent, false));
            case 1:
                return new ConsultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_consult, parent, false));
            case 2:
                return new GiftViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_gift, parent, false));
        }
        return null;
    }

    class ProneViewHolder extends RecyclerView.ViewHolder{

        RoundImageView iv_img;
        CheckBox cb_submit;
        AppCompatTextView tv_name;
        CircleImageView iv_head;
        View layout;

        public ProneViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            cb_submit = itemView.findViewById(R.id.cb_submit);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_head = itemView.findViewById(R.id.iv_head);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    class ConsultViewHolder extends RecyclerView.ViewHolder{

        RoundImageView iv_img;
        CheckBox cb_submit;
        AppCompatTextView tv_title;
        AppCompatTextView tv_time;

        public ConsultViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            cb_submit = itemView.findViewById(R.id.cb_submit);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

    class GiftViewHolder extends RecyclerView.ViewHolder {

        CheckBox cb_submit;
        AppCompatTextView tv_title;
        AppCompatTextView tv_add;
        TagFlowLayout fl_label;
        RoundImageView iv_img;
        RoundImageView iv_img1;
        RoundImageView iv_img2;

        public GiftViewHolder(View itemView) {
            super(itemView);
            cb_submit = itemView.findViewById(R.id.cb_submit);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_add = itemView.findViewById(R.id.tv_add);
            fl_label = itemView.findViewById(R.id.fl_label);
            iv_img = itemView.findViewById(R.id.iv_img);
            iv_img1 = itemView.findViewById(R.id.iv_img1);
            iv_img2 = itemView.findViewById(R.id.iv_img2);
        }
    }

}
