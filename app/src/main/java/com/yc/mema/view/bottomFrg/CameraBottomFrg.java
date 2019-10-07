package com.yc.mema.view.bottomFrg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.base.BaseBottomSheetFrg;

import java.util.ArrayList;


/**
 * 作者：yc on 2018/8/4.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 *  打开相册或相机
 */
@SuppressLint("ValidFragment")
public class CameraBottomFrg extends BaseBottomSheetFrg implements View.OnClickListener{


    private int type;

    public CameraBottomFrg() {
    }

    public CameraBottomFrg(int type) {
        this.type = type;
    }

    @Override
    protected void initParms(Bundle bundle) {

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

        ArrayList<LocalMedia> localMediaList = new ArrayList<>();
        for (LocalMedia media1 : localMediaList){

        }

        switch (type){
            case 0:
                break;
            case 1:
                view.findViewById(R.id.tv_camera).setVisibility(View.GONE);
                view.findViewById(R.id.tv_photo).setVisibility(View.GONE);
                view.findViewById(R.id.tv_save).setVisibility(View.VISIBLE);
                break;
            case 2:
                view.findViewById(R.id.tv_save).setVisibility(View.VISIBLE);
                break;
        }
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
                if (listener != null){
                    listener.save();
                }
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
        void save();
    }


}
