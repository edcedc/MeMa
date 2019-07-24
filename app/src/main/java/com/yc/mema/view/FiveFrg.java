package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.User;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FFiveBinding;
import com.yc.mema.impl.FiveContract;
import com.yc.mema.presenter.FivePresenter;
import com.yc.mema.utils.GlideLoadingUtils;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 14:47
 */
public class FiveFrg extends BaseFragment<FivePresenter, FFiveBinding> implements FiveContract.View, View.OnClickListener {

    public static FiveFrg newInstance() {

        Bundle args = new Bundle();

        FiveFrg fragment = new FiveFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    private boolean isRequest = true;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest){
            isRequest = false;
            mB.refreshLayout.startRefresh();
        }
        setData(User.getInstance().getUserObj());
    }


    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_five;
    }

    @Override
    protected void initView(View view) {
        mB.ivCol.setOnClickListener(this);
        mB.ivHead.setOnClickListener(this);
        mB.ivMsg.setOnClickListener(this);
        mB.tvBr.setOnClickListener(this);
        mB.tvBm.setOnClickListener(this);
        mB.tvSet.setOnClickListener(this);

        mB.refreshLayout.setEnableLoadmore(false);
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onInfo();
                mB.refreshLayout.finishRefreshing();
            }
        });

        GlideLoadingUtils.load(act, "", mB.ivHead);
        mB.tvName.setText("希腊时代");
        mB.tvId.setText("么马号：" +
                "M-19951215ae4f");
        mB.tvHp.setText("生日：" +
                "1995-12-15");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_col:
                UIHelper.startCollectionFrg(this);
                break;
            case R.id.iv_head:
                UIHelper.startUserInfoAct();
                break;
            case R.id.iv_msg:

                break;
            case R.id.tv_br:

                break;
            case R.id.tv_bm:

                break;
            case R.id.tv_set:

                break;
        }
    }

    @Override
    public void setData(JSONObject userObj) {

    }
}
