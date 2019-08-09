package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ComplaintAdapter;
import com.yc.mema.adapter.ReportNewsAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FReportNewsBinding;
import com.yc.mema.impl.ReportNewsContract;
import com.yc.mema.presenter.ReportNewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 22:24
 */
public class ReportNewsFrg extends BaseFragment<ReportNewsPresenter, FReportNewsBinding> implements ReportNewsContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private ReportNewsAdapter adapter;
    private int type;
    private String soId;
    private String complainId;
    private String infoId;
    private String discussId;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("type");
        infoId = bundle.getString("infoId");
        discussId = bundle.getString("discussId");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_report_news;
    }

    @Override
    protected void initView(View view) {
        mB.btSubmit.setOnClickListener(this);
        if (adapter == null){
            adapter = new ReportNewsAdapter(act, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(position -> {
            soId = listBean.get(position).getSoId();
            adapter.setPosition(position);
            adapter.notifyDataSetChanged();
        });

        mPresenter.onComplainList(type);
        mB.refreshLayout.setPureScrollModeOn();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(List<DataBean> list) {
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_submit:
                mPresenter.onReport(discussId, soId, type, infoId);
                break;
        }
    }

    @Override
    public void setComplain(DataBean result) {
        complainId = result.getComplainId();
        mPresenter.onRequest(complainId);
        mB.tvTitle.setText(result.getComplainName());
        setTitle(mB.tvTitle.getText().toString());
    }

    @Override
    public void setReport() {
        switch (type){
            case 7:
                pop();
                break;
        }
    }
}
