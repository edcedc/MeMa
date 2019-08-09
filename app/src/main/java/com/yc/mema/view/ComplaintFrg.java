package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.ComplaintAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BaseListContract;
import com.yc.mema.base.BaseListPresenter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FComplaintBinding;
import com.yc.mema.impl.ReportNewsContract;
import com.yc.mema.presenter.ReportNewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 16:35
 *  用户投诉反馈
 */
public class ComplaintFrg extends BaseFragment<ReportNewsPresenter, FComplaintBinding> implements ReportNewsContract.View {

    private List<DataBean> listBean = new ArrayList<>();
    private ComplaintAdapter adapter;
    private String id;
    private int type;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
        type = bundle.getInt("type");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_complaint;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.mema7));
        if (adapter == null){
            adapter = new ComplaintAdapter(act, this, listBean, id, type);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.setAdapter(adapter);
        mPresenter.onRequest(type + "");
    }

    @Override
    public void setData(List<DataBean> list) {
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setComplain(DataBean result) {

    }

    @Override
    public void setReport() {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (ReportFrg.isFinish){
            pop();
        }
    }
}
