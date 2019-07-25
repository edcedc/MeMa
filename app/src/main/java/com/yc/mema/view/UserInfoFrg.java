package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FInfoBinding;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;
import com.yc.mema.utils.DatePickerUtils;
import com.yc.mema.utils.GlideLoadingUtils;

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

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
//        setData(User.getInstance().getUserObj());
    }

    private void setData(JSONObject userObj) {
        if (userObj == null)return;
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

        GlideLoadingUtils.load(act, "", mB.ivHead);
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
                UIHelper.startMemaFrg(this);
                break;
            case R.id.ly_birthday:
                DatePickerUtils.getYearMonthDayPicker(act, "选择生日", new DatePickerUtils.OnYearMonthDayListener() {
                    @Override
                    public void onTime(String year, String month, String day) {
                        mB.tvBirthday.setText(year + "-" + month + "-" + day);
                        mPresenter.birthday(mB.tvBirthday.getText().toString());
                    }
                });
                break;
            case R.id.ly_sex:
                UIHelper.startSexFrg(this);
                break;
            case R.id.ly_address:
                UIHelper.startAddressFrg(this);
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
}
