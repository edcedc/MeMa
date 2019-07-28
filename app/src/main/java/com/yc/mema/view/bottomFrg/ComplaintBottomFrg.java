package com.yc.mema.view.bottomFrg;

import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseBottomSheetFrag;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 16:18
 */
public class ComplaintBottomFrg extends BaseBottomSheetFrag implements View.OnClickListener {

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
    public int bindLayout() {
        return R.layout.p_complaint;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_complaint).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }
}
