package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.base.User;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FInfoBinding;

import org.json.JSONObject;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 19:43
 *  用户信息
 */
public class UserInfoFrg extends BaseFragment<BasePresenter, FInfoBinding> implements View.OnClickListener {

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

                break;
            case R.id.ly_birthday:

                break;
            case R.id.ly_sex:

                break;
            case R.id.ly_address:

                break;
            case R.id.ly_zking:

                break;

        }
    }
}
