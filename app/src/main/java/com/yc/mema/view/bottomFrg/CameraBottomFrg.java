package com.yc.mema.view.bottomFrg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.base.BaseBottomSheetFrag;


/**
 * 作者：yc on 2018/8/4.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  打开相册或相机
 */
@SuppressLint("ValidFragment")
public class CameraBottomFrg extends BaseBottomSheetFrag implements View.OnClickListener{


    private boolean isSave = false;
    public CameraBottomFrg() {
    }

    public CameraBottomFrg(boolean b) {
        this.isSave = b;
    }

    @Override
    public int bindLayout() {
        return R.layout.p_camera;
    }

    @Override
    public void initView(View view) {
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        view.findViewById(R.id.tv_camera).setOnClickListener(this);
        view.findViewById(R.id.tv_photo).setOnClickListener(this);
        view.findViewById(R.id.tv_save).setOnClickListener(this);
        view.findViewById(R.id.layout).setOnClickListener(this);
        view.findViewById(R.id.tv_save).setVisibility(isSave ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
            case R.id.layout:
                dismiss();
                break;
             case R.id.tv_camera:
                if (listener != null){
                    listener.camera();
                }
                break;
             case R.id.tv_photo:
                 if (listener != null){
                     listener.photo();
                 }
                break;
            case R.id.tv_save:

                break;
        }
    }

    private onCameraListener listener;
    public void setCameraListener(onCameraListener listener){
        this.listener = listener;
    }

    public interface onCameraListener{
        void camera();
        void photo();
    }


}
