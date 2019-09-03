package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.mema.R;
import com.yc.mema.adapter.GiftDescAdapter;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FGiftBinding;
import com.yc.mema.impl.GiftDescContract;
import com.yc.mema.presenter.GiftDescPresenter;
import com.yc.mema.utils.Constants;
import com.yc.mema.view.bottomFrg.ComplaintBottomFrg;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/27
 * Time: 18:42
 *  礼包
 */
public class GiftDescFrg extends BaseFragment<GiftDescPresenter, FGiftBinding> implements GiftDescContract.View, View.OnClickListener {

    private String id;
    private int getpIsTrue;
    private int getcIsTrue;

    public static GiftDescFrg newInstance() {

        Bundle args = new Bundle();

        GiftDescFrg fragment = new GiftDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private ComplaintBottomFrg complaintBottomFrg;

    private List<DataBean> listBean = new ArrayList<>();
    private GiftDescAdapter adapter;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_gift;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.gift_desc), R.mipmap.y20);
        mB.tvLike.setOnClickListener(this);
        mB.tvColl.setOnClickListener(this);
        mB.refreshLayout.setPureScrollModeOn();
        if (adapter == null){
            adapter = new GiftDescAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        mPresenter.onRequest(id);
        complaintBottomFrg = new ComplaintBottomFrg();
        complaintBottomFrg.setComplaintListener(() -> {
            if (!((BaseActivity)act).isLogin())return;
            UIHelper.startComplaintFrg(GiftDescFrg.this, id, Constants.CAUSES_WELFARE_COMPLAINTS);
        });
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        ReportFrg.isFinish = false;
    }

    @Override
    public void onClick(View view) {
        if (!((BaseActivity)act).isLogin())return;
        switch (view.getId()){
            case R.id.tv_like:
                mPresenter.onZan(id, getpIsTrue);
                break;
            case R.id.tv_coll:
                mPresenter.onColl(id, getcIsTrue);
                break;
        }
    }

    @Override
    public void setData(DataBean bean) {
        mB.tvTitle.setText(bean.getWalTitle());
        mB.tvLike.setText(bean.getPraiseCount() + "");
        mB.tvColl.setText(bean.getCollectCount() + "");
        mB.tvLock.setText(bean.getBrowseCount() + "");
        mB.tvTime.setText("营业时间：" + bean.getBusinessTime());
        mB.tvPhone.setText(bean.getIphone());
        mB.tvAddress.setText("商家地址：" +
                bean.getPcyAdd() + bean.getAddress());
        String tips = bean.getTips();
        mB.tvCozy.setVisibility(tips == null ? View.GONE : View.VISIBLE);
        mB.tvCozy.setText(tips == null ? "" : tips);

        String discount = bean.getDiscount();
        if (!StringUtils.isEmpty(discount)){
            String[] split = discount.split("，");
            mB.flLabel.removeAllViews();
            mB.flLabel.setAdapter(new TagAdapter<String>(split){
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
        if (welfareImgs != null && welfareImgs.size() != 0){
            listBean.addAll(welfareImgs);
            adapter.notifyDataSetChanged();
        }
        getpIsTrue = bean.getpIsTrue();
        getcIsTrue = bean.getcIsTrue();
        setInfoZanState(getpIsTrue);
        setCollState(getcIsTrue);
    }

    @Override
    public void setZan(int finalType) {
        this.getpIsTrue = finalType;
        setInfoZanState(finalType);
        Integer collNum = Integer.valueOf(mB.tvLike.getText().toString());
        mB.tvLike.setText((finalType == 0 ? collNum - 1 : collNum + 1) + "");
    }

    @Override
    public void setColl(int finalType) {
        this.getcIsTrue = finalType;
        setCollState(finalType);
        Integer collNum = Integer.valueOf(mB.tvColl.getText().toString());
        mB.tvColl.setText((finalType == 0 ? collNum - 1 : collNum + 1) + "");
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        complaintBottomFrg.show(getChildFragmentManager(), "dialog");
    }

    private void setInfoZanState(int isTrue) {
        if (isTrue == 0) {
            mB.tvLike.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y24, null),
                    null, null, null);
        } else {
            mB.tvLike.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y44, null),
                    null, null, null);
        }
    }

    private void setCollState(int isTrue) {
        if (isTrue == 0) {
            mB.tvColl.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y25, null),
                    null, null, null);
        } else {
            mB.tvColl.setCompoundDrawablesWithIntrinsicBounds(act.getResources().getDrawable(R.mipmap.y46, null),
                    null, null, null);
        }
    }

}
