package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FMemaBinding;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;
import com.yc.mema.utils.DatePickerUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 11:00
 *  么马号
 */
public class MemaFrg extends BaseFragment<InformationPresenter, FMemaBinding> implements InformationContract.View, View.OnClickListener {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_mema;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.set_mema), getString(R.string.submit1));
        mB.tvTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_time:
                DatePickerUtils.getYearMonthDayPicker(act, "选择时间", ((year, month, day) ->{
                    mB.tvTime.setText(year + "-" + month + "-" + day);
                } ));
                break;
        }
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        mPresenter.mema(mB.tvTime.getText().toString(), mB.etText.getText().toString(), 1);
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onSaveUser() {
        pop();
    }
}
