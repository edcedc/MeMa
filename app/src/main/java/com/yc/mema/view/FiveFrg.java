package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.StringUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.User;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FFiveBinding;
import com.yc.mema.impl.FiveContract;
import com.yc.mema.presenter.FivePresenter;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.cache.ShareSessionIdCache;

import org.json.JSONException;
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
        if (isRequest) {
            if (!((BaseActivity)act).isLogin())return;
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
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_col:
                if (!((BaseActivity) act).isLogin()) return;
                UIHelper.startCollectionFrg(this);
                break;
            case R.id.iv_head:
                if (!((BaseActivity) act).isLogin()) return;
                UIHelper.startUserInfoAct();
                break;
            case R.id.iv_msg:
                UIHelper.startMessageFrg(this);
                break;
            case R.id.tv_br:
                if (!((BaseActivity) act).isLogin()) return;
                UIHelper.startProneFrg(this);
                break;
            case R.id.tv_bm:
                if (!((BaseActivity) act).isLogin()) return;
                UIHelper.startBirthdayRecordsFrg(this);
                break;
            case R.id.tv_set:
                UIHelper.startSetAct();
                break;
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(JSONObject userObj) {
        if (userObj == null)return;
        mB.refreshLayout.finishRefreshing();
        GlideLoadingUtils.load(act, userObj.optString("headUrl"), mB.ivHead, true);
        mB.tvName.setText(userObj.optString("nickName"));
        mB.tvId.setText("么马号：" + userObj.optString("mema"));
        mB.tvHp.setText("生日：" + userObj.optString("birthday"));
    }

}
