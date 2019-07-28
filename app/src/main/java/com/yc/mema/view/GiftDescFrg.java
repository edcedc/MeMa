package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        mPresenter.onRequest();
        complaintBottomFrg = new ComplaintBottomFrg();
        complaintBottomFrg.setComplaintListener(new ComplaintBottomFrg.onComplaintListener() {
            @Override
            public void onComplaint() {
                UIHelper.startComplaintFrg(GiftDescFrg.this);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    @Override
    public void setData(DataBean bean) {
        mB.tvTitle.setText("胡桃里音乐酒馆 生日免费送特色菜，超多礼包等你来拿");
        mB.tvLike.setText("0");
        mB.tvColl.setText("0");
        mB.tvLock.setText("0");
        mB.tvTime.setText("营业时间：" +
                "9：00-21：00");
        mB.tvPhone.setText("020-88987689");
        mB.tvAddress.setText("商家地址：" +
                "广州  天河区  289艺术创意园34-36号");

        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        list.add(new DataBean());
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
        mB.flLabel.removeAllViews();
        mB.flLabel.setAdapter(new TagAdapter<DataBean>(list){
            @Override
            public View getView(FlowLayout parent, int position, DataBean dataBean) {
                View view = View.inflate(act, R.layout.i_gift_label, null);
                RoundTextView tvText = view.findViewById(R.id.tv_text);
                RoundViewDelegate delegate = tvText.getDelegate();
                delegate.setBackgroundColor(act.getColor(R.color.red_EE3257));
                tvText.setTextColor(act.getColor(R.color.white));
                tvText.setText(position + "全部");
                return view;
            }
        });
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        complaintBottomFrg.show(getChildFragmentManager(), "dialog");
    }
}
