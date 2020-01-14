package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.umeng.socialize.ShareAction;
import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.User;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FFiveBinding;
import com.yc.mema.impl.FiveContract;
import com.yc.mema.presenter.FivePresenter;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.ShareTool;
import com.yc.mema.view.act.ShareAct;

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

    private ShareAction shareAction;

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (isRequest) {
            if (!((BaseActivity) act).isLogin()) return;
            mB.refreshLayout.startRefresh();
            mB.refreshLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        setData(User.getInstance().getUserObj());
        String s = "";
        String[] split = s.split(",");
        for (int i = 0;i < split.length;i++){

        }
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
        mB.tvCollection.setOnClickListener(this);
        mB.ivHead.setOnClickListener(this);
        mB.ivZking.setOnClickListener(this);
        mB.tvMsg.setOnClickListener(this);
        mB.tvBr.setOnClickListener(this);
        mB.tvBm.setOnClickListener(this);
        mB.tvSet.setOnClickListener(this);
        mB.tvShare.setOnClickListener(this);
        mB.tvApply.setOnClickListener(this);
        mB.tvLock.setOnClickListener(this);
        mB.tvPayment.setOnClickListener(this);
        mB.tvShipped.setOnClickListener(this);
        mB.tvReceived.setOnClickListener(this);
        mB.tvEvaluate.setOnClickListener(this);
        mB.tvEntry.setOnClickListener(this);
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
        if (isRequest) return;
        switch (view.getId()) {
            case R.id.tv_collection:
                UIHelper.startCollectionFrg(this);
                break;
            case R.id.iv_head:
                UIHelper.startUserInfoAct();
                break;
            case R.id.tv_msg:
                UIHelper.startMessageFrg(this);
                break;
            case R.id.tv_br:
                UIHelper.startProneFrg(this);
                break;
            case R.id.tv_bm:
                UIHelper.startBirthdayRecordsFrg(this);
                break;
            case R.id.tv_set:
                UIHelper.startSetAct();
                break;
            case R.id.tv_share:
                shareAction = ShareTool.getInstance(act).shareAction(CloudApi.SHARE_BUSINESS_URL + User.getInstance().getUserId(), "你生日我买单", "海量优质商家入驻，为生日用户提供各种优惠福利。大家都在用这款炒鸡好的生日APP。");
                shareAction.open();
//                UIHelper.startShareFrg(this);
                break;
            case R.id.tv_apply:
//                UIHelper.startApplyFrg(this);
                mPresenter.onaUserAgent(this);
                break;
            case R.id.tv_address:
                UIHelper.startShopAddressFrg(this);
                break;
            case R.id.tv_lock:
            case R.id.tv_payment:
                UIHelper.startOrderListAct(0);
                break;
            case R.id.tv_shipped:
                UIHelper.startOrderListAct(1);
                break;
            case R.id.tv_received:
                UIHelper.startOrderListAct(2);
                break;
            case R.id.tv_evaluate:
                UIHelper.startOrderListAct(3);
                break;
            case R.id.iv_zking:
                UIHelper.startZkingFrg(this);
                break;
            case R.id.tv_entry:
                mPresenter.onBusinessGetBusiness();
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
        isRequest = false;
        if (userObj == null) return;
        mB.refreshLayout.finishRefreshing();
        GlideLoadingUtils.load(act, userObj.optString("headUrl"), mB.ivHead, true);
        mB.tvName.setText(userObj.optString("nickName"));
        mB.tvId.setText("么马号：" + userObj.optString("mema"));
        mB.tvHp.setText("生日：" + userObj.optString("birthday"));
    }

    @Override
    public void setDateError() {
        UIHelper.startLoginAct();
        showToast("请登录");
    }

    @Override
    public void setGetBusiness(int handle, String reason) {
        UIHelper.starTentryFrg(this, handle, reason);
    }

}
