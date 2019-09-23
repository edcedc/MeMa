package com.yc.mema.view.bottomFrg;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseBottomSheetFrg;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 16:18
 */
public class ReportBottomFrg extends BaseBottomSheetFrg implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_complaint:
                if (listener != null){
                    listener.onComplaint();
                }
                dismiss();
                break;
        }
    }

    private onComplaintListener listener;
    public void setComplaintListener(onComplaintListener listener){
        this.listener = listener;
    }

    public interface onComplaintListener{
        void onComplaint();
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.p_report;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_complaint).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }
}
