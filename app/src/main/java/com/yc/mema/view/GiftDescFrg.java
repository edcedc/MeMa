package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.yc.mema.R;
import com.yc.mema.adapter.GiftDescAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FGiftBinding;
import com.yc.mema.impl.GiftDescContract;
import com.yc.mema.presenter.GiftDescPresenter;
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
        mB.refreshLayout.setPureScrollModeOn();
        if (adapter == null){
            adapter = new GiftDescAdapter(act, listBean);
        }
        mB.recyclerView.setAdapter(adapter);
        mPresenter.onRequest(id);
        complaintBottomFrg = new ComplaintBottomFrg();
        complaintBottomFrg.setComplaintListener(() -> UIHelper.startComplaintFrg(GiftDescFrg.this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void setData(DataBean bean) {
        mB.tvTitle.setText(bean.getWalTitle());
        mB.tvLike.setText(bean.getPraiseCount() + "");
        mB.tvColl.setText(bean.getCollectCount() + "");
        mB.tvLock.setText(bean.getBrowseCount() + "");
        mB.tvTime.setText(bean.getBusinessTime());
        mB.tvPhone.setText(bean.getIphone());
        mB.tvAddress.setText("商家地址：" +
                bean.getPcyAdd() + bean.getAddress());
        String discount = bean.getDiscount();
        if (!StringUtils.isEmpty(discount)){
            String[] split = discount.split(",");
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
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        complaintBottomFrg.show(getChildFragmentManager(), "dialog");
    }
}
