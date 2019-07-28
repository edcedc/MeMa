package com.yc.mema.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.mema.R;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
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

    public CollectionAdapter(Context act, List<DataBean> listBean, int type) {
        super(act, listBean);
        this.type = type;
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
            showImg(viewHolder.iv_img);
            showImg(viewHolder.iv_head);
            viewHolder.tv_name.setText("番号");
            isDel(viewHolder.cb_submit);
            viewHolder.cb_submit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    bean.setSelect(b);
                }
            });
            viewHolder.cb_submit.setChecked(bean.isSelect());
        }else if (holder instanceof ConsultViewHolder){
            ConsultViewHolder viewHolder = (ConsultViewHolder) holder;
            showImg(viewHolder.iv_img);
            viewHolder.tv_title.setText("首届世界人道主义峰会在土耳其闭幕，与会方共作出1500项承诺");
            viewHolder.tv_time.setText("2019-05-24");
            isDel(viewHolder.cb_submit);
            viewHolder.cb_submit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    bean.setSelect(b);
                }
            });
        }else if (holder instanceof GiftViewHolder){
            GiftViewHolder viewHolder = (GiftViewHolder) holder;
            showImg(viewHolder.iv_img);
            showImg(viewHolder.iv_img1);
            showImg(viewHolder.iv_img2);
            viewHolder.tv_title.setText("胡桃里音乐酒馆 ");
            viewHolder.tv_add.setText("广州" +
                    "·" +
                    "天河 ");

            List<DataBean> list = new ArrayList<>();
            list.add(new DataBean());
            list.add(new DataBean());
            list.add(new DataBean());
            viewHolder.fl_label.removeAllViews();
            viewHolder.fl_label.setAdapter(new TagAdapter<DataBean>(list){
                @Override
                public View getView(FlowLayout parent, int position, DataBean dataBean) {
                    View view = View.inflate(act, R.layout.i_gift_label, null);
                    TextView tvText = view.findViewById(R.id.tv_text);
                    tvText.setText(position + "全部");
                    return view;
                }
            });
            isDel(viewHolder.cb_submit);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UIHelper.startGiftAct();
                }
            });
        }
    }

    private void isDel(CheckBox box){
        box.setVisibility(isDel ? View.VISIBLE : View.GONE);


    }

    private void showImg(ImageView imageView){
        GlideLoadingUtils.load(act, "", imageView);
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

        public ProneViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            cb_submit = itemView.findViewById(R.id.cb_submit);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_head = itemView.findViewById(R.id.iv_head);
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
