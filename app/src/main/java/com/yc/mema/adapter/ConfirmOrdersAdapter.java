package com.yc.mema.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundTextView;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.utils.Constants;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.view.act.HtmlAct;

import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/10
 * Time: 15:28
 */
public class ConfirmOrdersAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public ConfirmOrdersAdapter(Context act, BaseFragment root, List<DataBean> listBean) {
        super(act, root, listBean);
    }
    public ConfirmOrdersAdapter(Context act, List<DataBean> listBean) {
        super(act,  listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        List<DataBean> goodSupImgs = bean.getGoodSpuImgs();
        if (goodSupImgs != null && goodSupImgs.size() != 0){
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + goodSupImgs.get(0).getAttachId(), viewHolder.iv_img);
        }
        viewHolder.tv_title.setText(bean.getGoodName());
        viewHolder.tv_content.setText("已选：" + "\"" + bean.getGoodSku() + "\"");
        SpannableString hText = new SpannableString("¥" + bean.getPrice());
        hText.setSpan(new AbsoluteSizeSpan(9, true), 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        viewHolder.tv_price.setText(hText);
        viewHolder.tv_num.setText("x" + bean.getGoodNumber());


        viewHolder.tv_size.setText("共" +
                "1" +
                "件商品合计：");
        viewHolder.tv_all_price.setText("¥" +
                bean.getAllPrice());
        viewHolder.tv_freight.setText("（含运费¥" +
                "0.00" +
                "）");

        String orderNum = bean.getOrderNum();
        if (StringUtils.isEmpty(orderNum)){
            viewHolder.gp_order_bottom.setVisibility(View.GONE);
            viewHolder.gp_order_num.setVisibility(View.GONE);
        }else {
            viewHolder.gp_order_bottom.setVisibility(View.VISIBLE);
            viewHolder.gp_order_num.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            viewHolder.tv_order_num.setText("订单编号：" + orderNum);
            switch (bean.getStatus()){
                case Constants.waitPay:
                    viewHolder.tv_state.setText(act.getResources().getText(R.string.pending_payment));
                    viewHolder.tv_pay.setVisibility(View.VISIBLE);
                    viewHolder.tv_call_order.setVisibility(View.VISIBLE);
                    viewHolder.tv_confirm.setVisibility(View.GONE);
                    viewHolder.tv_evaluate.setVisibility(View.GONE);
                    viewHolder.tv_look_wuliu.setVisibility(View.GONE);
                    viewHolder.tv_apply_refund.setVisibility(View.GONE);
                    break;
                case Constants.successPay:
                    viewHolder.tv_state.setText(act.getResources().getText(R.string.to_shipped));
                    viewHolder.tv_pay.setVisibility(View.GONE);
                    viewHolder.tv_call_order.setVisibility(View.GONE);
                    viewHolder.tv_confirm.setVisibility(View.GONE);
                    viewHolder.tv_evaluate.setVisibility(View.GONE);
                    viewHolder.tv_look_wuliu.setVisibility(View.GONE);
                    viewHolder.tv_apply_refund.setVisibility(View.VISIBLE);
                    break;
                case Constants.waitDeliver:
                    viewHolder.tv_state.setText(act.getResources().getText(R.string.goods_received));
                    viewHolder.tv_pay.setVisibility(View.GONE);
                    viewHolder.tv_call_order.setVisibility(View.GONE);
                    viewHolder.tv_confirm.setVisibility(View.VISIBLE);
                    viewHolder.tv_evaluate.setVisibility(View.GONE);
                    viewHolder.tv_look_wuliu.setVisibility(View.VISIBLE);
                    viewHolder.tv_apply_refund.setVisibility(View.VISIBLE);
                    break;
                case Constants.successDeliver:
                    if (bean.getIsAppraise() == 0){
                        viewHolder.tv_state.setText(act.getResources().getText(R.string.to_evaluated));
                        viewHolder.tv_pay.setVisibility(View.GONE);
                        viewHolder.tv_call_order.setVisibility(View.GONE);
                        viewHolder.tv_confirm.setVisibility(View.GONE);
                        viewHolder.tv_evaluate.setVisibility(View.VISIBLE);
                        viewHolder.tv_look_wuliu.setVisibility(View.VISIBLE);
                        viewHolder.tv_apply_refund.setVisibility(View.GONE);
                    }else {
                        viewHolder.tv_state.setText(act.getResources().getText(R.string.completed));
                        viewHolder.tv_pay.setVisibility(View.GONE);
                        viewHolder.tv_call_order.setVisibility(View.GONE);
                        viewHolder.tv_confirm.setVisibility(View.GONE);
                        viewHolder.tv_evaluate.setVisibility(View.GONE);
                        viewHolder.tv_look_wuliu.setVisibility(View.VISIBLE);
                        viewHolder.tv_apply_refund.setVisibility(View.GONE);
                    }
                    break;
            }
        }
        viewHolder.itemView.setOnClickListener(view -> {
            if (root != null){
                UIHelper.startOrderDescFrg(root, bean.getOrderId());
            }
        });
        viewHolder.tv_pay.setOnClickListener(view -> UIHelper.startOrderDescFrg(root, bean.getOrderId()));
        viewHolder.tv_confirm.setOnClickListener(view -> listener.onConfirm(position, bean.getOrderId()));
        viewHolder.tv_call_order.setOnClickListener(view -> listener.onCallOrder(position, bean.getOrderId()));
        viewHolder.tv_apply_refund.setOnClickListener(view -> {

        });
        viewHolder.tv_evaluate.setOnClickListener(view -> {
            UIHelper.startEvaluateFrg(bean);
        });
        viewHolder.tv_look_wuliu.setOnClickListener(view -> {
            String url = CloudApi.WULIU_URL + bean.getExpCode() + "&expressNo=" + bean.getExpressNo();
            UIHelper.startHtmlAct(HtmlAct.LOOK_WULIU, url);
        });
    }

    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }
    public interface OnClickListener{
        void onConfirm(int position, String goodId);
        void onCallOrder(int position, String goodId);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_con_order, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatImageView iv_img;
        AppCompatTextView tv_title;
        AppCompatTextView tv_content;
        AppCompatTextView tv_price;
        AppCompatTextView tv_num;
        AppCompatTextView tv_order_num;
        AppCompatTextView tv_state;
        AppCompatTextView tv_size;
        AppCompatTextView tv_all_price;
        AppCompatTextView tv_freight;
        Group gp_order_num;
        Group gp_order_bottom;
        RoundTextView tv_pay;
        RoundTextView tv_call_order;
        RoundTextView tv_confirm;
        RoundTextView tv_evaluate;
        RoundTextView tv_look_wuliu;
        RoundTextView tv_apply_refund;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_order_num = itemView.findViewById(R.id.tv_order_num);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_all_price = itemView.findViewById(R.id.tv_all_price);
            tv_freight = itemView.findViewById(R.id.tv_freight);
            gp_order_num = itemView.findViewById(R.id.gp_order_num);
            gp_order_bottom = itemView.findViewById(R.id.gp_order_bottom);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            tv_call_order = itemView.findViewById(R.id.tv_call_order);
            tv_confirm = itemView.findViewById(R.id.tv_confirm);
            tv_evaluate = itemView.findViewById(R.id.tv_evaluate);
            tv_look_wuliu = itemView.findViewById(R.id.tv_look_wuliu);
            tv_apply_refund = itemView.findViewById(R.id.tv_apply_refund);
        }
    }

}
