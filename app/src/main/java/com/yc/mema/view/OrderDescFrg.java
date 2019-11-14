package com.yc.mema.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.yc.mema.R;
import com.yc.mema.adapter.ConfirmOrdersAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FShopDescBinding;
import com.yc.mema.event.PayInEvent;
import com.yc.mema.event.RefreshOrderListInEvent;
import com.yc.mema.impl.OrderDescContract;
import com.yc.mema.presenter.OrderDescPresenter;
import com.yc.mema.utils.Constants;
import com.yc.mema.utils.PopupWindowTool;
import com.yc.mema.view.act.HtmlAct;
import com.yc.mema.view.bottomFrg.PayBottomFrg;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/12
 * Time: 14:34
 */
public class OrderDescFrg extends BaseFragment<OrderDescPresenter, FShopDescBinding> implements OrderDescContract.View, View.OnClickListener {

    public static OrderDescFrg newInstance() {
        
        Bundle args = new Bundle();
        
        OrderDescFrg fragment = new OrderDescFrg();
        fragment.setArguments(args);
        return fragment;
    }
    
    private String orderId;

    private List<DataBean> listBean = new ArrayList<>();
    private ConfirmOrdersAdapter adapter;
    private PayBottomFrg payBottomFrg;
    private int payPosition;
    private String expCode;
    private String expressNo;


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        orderId = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_shop_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.order_desc));
        mB.refreshLayout.setPureScrollModeOn();
        mB.tvApplyRefund.setOnClickListener(this);
        mB.tvLookWuliu.setOnClickListener(this);
        mB.tvEvaluate.setOnClickListener(this);
        mB.tvConfirm.setOnClickListener(this);
        mB.tvCallOrder.setOnClickListener(this);
        mB.tvPay.setOnClickListener(this);
        if (adapter == null){
            adapter = new ConfirmOrdersAdapter(act, this, listBean);
        }

        setRecyclerViewType(mB.recyclerView, R.color.white);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.recyclerView.setAdapter(adapter);
        mPresenter.onRequest(orderId);
        payBottomFrg = new PayBottomFrg();
        payBottomFrg.setOnClickListener(position -> {
            payPosition = position;
            switch (position){
                case 0:
                    mPresenter.onWxPay(orderId, position);
                    break;
                case 1:
                    showToast("暂未开通服务");
                    break;
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_pay:
                payBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
            case R.id.tv_apply_refund:

                break;
            case R.id.tv_look_wuliu:
                String url = CloudApi.WULIU_URL + expCode + "&expressNo=" + expressNo;
                UIHelper.startHtmlAct(HtmlAct.LOOK_WULIU, url);
                break;
            case R.id.tv_evaluate:
                UIHelper.startEvaluateFrg(listBean.get(0));
                break;
            case R.id.tv_confirm:
                PopupWindowTool.showDialog(act)
                        .asConfirm("确定收货吗？", "",
                                "取消", "确定",
                                () -> {
                                    mPresenter.onUpdateOrder(orderId, Constants.successDeliver);
                                }, null, false)
                        .bindLayout(R.layout.p_dialog) //绑定已有布局
                        .show();
                break;
            case R.id.tv_call_order:
                PopupWindowTool.showDialog(act)
                        .asConfirm("确定取消订单吗？", "",
                                "取消", "确定",
                                () -> {
                                    mPresenter.onUpdateOrder(orderId, Constants.waitHarvest);
                                }, null, false)
                        .bindLayout(R.layout.p_dialog) //绑定已有布局
                        .show();
                break;
        }
    }

    @Override
    public void setData(DataBean bean) {
        expCode = bean.getExpCode();
        expressNo = bean.getExpressNo();
        Bundle bundle = new Bundle();
        bundle.putDouble("price", bean.getAllPrice());
        payBottomFrg.setArguments(bundle);

        String userName = bean.getUserName();
        String iphone = bean.getIphone();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#999999"));
        SpannableString cText = new SpannableString("收件人：" + userName + "   " + iphone + "\n" + bean.getAddress());
        cText.setSpan(colorSpan, bean.getUserName().length() + 7, bean.getUserName().length() + 7 + iphone.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mB.tvAddress.setText(cText);

        StringBuffer sb = new StringBuffer();
        sb.append("订单编号：" + bean.getOrderNum()).append("\n").append("\n" + "创建时间：" + bean.getCreateTime());
        String payTime = bean.getPayTime();
        if (!StringUtils.isEmpty(payTime)){
            sb.append("\n").append("\n" + "支付时间：" + payTime);
        }
        String deliveryTime = bean.getDeliveryTime();
        if (!StringUtils.isEmpty(deliveryTime)){
            sb.append("\n").append("\n" + "发货时间：" + deliveryTime);
        }
        String receiveTime = bean.getReceiveTime();
        if (!StringUtils.isEmpty(receiveTime)){
            sb.append("\n").append("\n" + "收货时间：" + receiveTime);
        }
        String cancelTime = bean.getCancelTime();
        if (!StringUtils.isEmpty(cancelTime)){
            sb.append("\n").append("\n" + "取消时间：" + cancelTime);
        }
        mB.tvDesc.setText(sb.toString());

        switch (bean.getStatus()){
            case Constants.waitPay:
                mB.tvState.setText(act.getResources().getText(R.string.pending_payment));
                mB.tvPay.setVisibility(View.VISIBLE);
                mB.tvCallOrder.setVisibility(View.VISIBLE);
                mB.tvConfirm.setVisibility(View.GONE);
                mB.tvEvaluate.setVisibility(View.GONE);
                mB.tvLookWuliu.setVisibility(View.GONE);
                mB.tvApplyRefund.setVisibility(View.GONE);
                break;
            case Constants.successPay:
                mB.tvState.setText(act.getResources().getText(R.string.to_shipped));
                mB.tvPay.setVisibility(View.GONE);
                mB.tvCallOrder.setVisibility(View.GONE);
                mB.tvConfirm.setVisibility(View.GONE);
                mB.tvEvaluate.setVisibility(View.GONE);
                mB.tvLookWuliu.setVisibility(View.GONE);
                mB.tvApplyRefund.setVisibility(View.VISIBLE);
                break;
            case Constants.waitDeliver:
                mB.tvState.setText(act.getResources().getText(R.string.goods_received));
                mB.tvPay.setVisibility(View.GONE);
                mB.tvCallOrder.setVisibility(View.GONE);
                mB.tvConfirm.setVisibility(View.VISIBLE);
                mB.tvEvaluate.setVisibility(View.GONE);
                mB.tvLookWuliu.setVisibility(View.VISIBLE);
                mB.tvApplyRefund.setVisibility(View.VISIBLE);
                break;
            case Constants.successDeliver:
                if (bean.getIsAppraise() == 0){
                    mB.tvState.setText(act.getResources().getText(R.string.to_evaluated));
                    mB.tvPay.setVisibility(View.GONE);
                    mB.tvCallOrder.setVisibility(View.GONE);
                    mB.tvConfirm.setVisibility(View.GONE);
                    mB.tvEvaluate.setVisibility(View.VISIBLE);
                    mB.tvLookWuliu.setVisibility(View.VISIBLE);
                    mB.tvApplyRefund.setVisibility(View.GONE);
                }else {
                    mB.tvState.setText(act.getResources().getText(R.string.completed));
                    mB.tvPay.setVisibility(View.GONE);
                    mB.tvCallOrder.setVisibility(View.GONE);
                    mB.tvConfirm.setVisibility(View.GONE);
                    mB.tvEvaluate.setVisibility(View.GONE);
                    mB.tvLookWuliu.setVisibility(View.VISIBLE);
                    mB.tvApplyRefund.setVisibility(View.GONE);
                }
                break;
            case Constants.waitHarvest:
                mB.tvState.setText(act.getResources().getText(R.string.call_order));
                mB.layout.setVisibility(View.GONE);
                break;
        }
        bean.setOrderNum(null);
        listBean.clear();
        listBean.add(bean);
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onMainPayInEvent(PayInEvent event){
        setRefresh();
    }

    @Override
    public void setRefresh() {
        EventBus.getDefault().post(new RefreshOrderListInEvent());
        mPresenter.onRequest(orderId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
