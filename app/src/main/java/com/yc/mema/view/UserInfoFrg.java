package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.bean.AddressBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FInfoBinding;
import com.yc.mema.event.AddressInEvent;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;
import com.yc.mema.utils.DatePickerUtils;
import com.yc.mema.utils.GlideLoadingUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 19:43
 *  用户信息
 */
public class UserInfoFrg extends BaseFragment<InformationPresenter, FInfoBinding> implements InformationContract.View, View.OnClickListener {

    public static UserInfoFrg newInstance() {
        
        Bundle args = new Bundle();
        
        UserInfoFrg fragment = new UserInfoFrg();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        setData(User.getInstance().getUserObj());
    }

    private void setData(JSONObject userObj) {
        if (userObj == null)return;
        GlideLoadingUtils.load(act, userObj.optString("headUrl"), mB.ivHead, true);
        mB.tvName.setText(userObj.optString("nickName"));
        mB.tvMoma.setText(userObj.optString("mema"));
        mB.tvBirthday.setText(userObj.optString("birthday"));
        int sex = userObj.optInt("sex");
        mB.tvSex.setText(sex <= 0 ? "" : sex == 1 ? "男" : "女");
        mB.tvAddress.setText(userObj.optString("address"));
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_info;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.user_info));
        mB.ivHead.setOnClickListener(this);
        mB.lyName.setOnClickListener(this);
        mB.lyMoma.setOnClickListener(this);
        mB.lyBirthday.setOnClickListener(this);
        mB.lySex.setOnClickListener(this);
        mB.lyAddress.setOnClickListener(this);
        mB.lyZking.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head:
                UIHelper.startHeadFrg(this);
                break;
            case R.id.ly_name:
                UIHelper.startUpdateNameFrg(this);
                break;
            case R.id.ly_moma:
                JSONObject userObj = User.getInstance().getUserObj();
                if (userObj.optInt("updataMema") != 0){
                    showToast("么马号只能设置一次");
                    return;
                }
                UIHelper.startMemaFrg(this);
                break;
            case R.id.ly_birthday:
                DatePickerUtils.getYearMonthDayPicker(act, "选择生日", (year, month, day) -> {
                    mB.tvBirthday.setText(year + "-" + month + "-" + day);
                    mPresenter.birthday(mB.tvBirthday.getText().toString());
                });
                break;
            case R.id.ly_sex:
                UIHelper.startSexFrg(this);
                break;
            case R.id.ly_address:
                UIHelper.startAddressAct(AddressInEvent.USER_INFP_TYPE);
                break;
            case R.id.ly_zking:
                UIHelper.startZkingFrg(this);
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onSaveUser() {
        setData(User.getInstance().getUserObj());
    }

    @Subscribe
    public void onMainAddressInEvent(AddressInEvent event){
        if (event.type != AddressInEvent.USER_INFP_TYPE)return;
        mPresenter.address(event.parentId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
