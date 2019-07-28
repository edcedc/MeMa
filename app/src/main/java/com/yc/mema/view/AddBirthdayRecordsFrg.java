package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FAddBhBinding;
import com.yc.mema.impl.AddBirthdayRecordsContract;
import com.yc.mema.presenter.AddBirthdayRecordsPresenter;
import com.yc.mema.utils.DatePickerUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/26
 * Time: 16:52
 *  添加生日记录
 */
public class AddBirthdayRecordsFrg extends BaseFragment<AddBirthdayRecordsPresenter, FAddBhBinding> implements AddBirthdayRecordsContract.View {

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_add_bh;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.add_records1), getString(R.string.submit1));
        mB.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerUtils.getYearMonthDayPicker(act, "选择生日", new DatePickerUtils.OnYearMonthDayListener() {
                    @Override
                    public void onTime(String year, String month, String day) {
                        mB.tvTime.setText(year + "-" + month + "-" + day);
                    }
                });
            }
        });
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        mPresenter.add(mB.etText.getText().toString(), mB.tvTime.getText().toString());
    }
}
