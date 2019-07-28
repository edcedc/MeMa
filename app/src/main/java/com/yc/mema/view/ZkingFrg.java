package com.yc.mema.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.blankj.utilcode.util.TimeUtils;
import com.google.zxing.WriterException;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.databinding.FZkingBinding;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.utils.ImageUtils;
import com.yc.mema.view.bottomFrg.CameraBottomFrg;
import com.yc.mema.weight.PictureSelectorTool;
import com.yc.mema.weight.ZXingUtils;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/25
 * Time: 15:17
 *  我的二维码
 */
public class ZkingFrg extends BaseFragment<BasePresenter, FZkingBinding> {

    private CameraBottomFrg cameraBottomFrg;

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_zking;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.me_zking), R.mipmap.y20);
        GlideLoadingUtils.load(act, "", mB.ivHead);
        mB.tvName.setText("z繁浩");
        mB.tvBirthday.setText("生日：" +
                "1995-12-15");
        mB.ivZking.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mB.ivZking.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                try {
                    Bitmap bitmap = ZXingUtils.creatBarcode("https://www.baidu.com/", mB.ivZking.getWidth());
                    mB.ivZking.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        if (cameraBottomFrg == null){
            cameraBottomFrg = new CameraBottomFrg(1);
        }
        cameraBottomFrg.setCameraListener(new CameraBottomFrg.onCameraListener() {
            @Override
            public void camera() {
                PictureSelectorTool.PictureSelectorImage(act, CameraInEvent.HEAD_CAMEAR, false);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

            @Override
            public void photo() {
                PictureSelectorTool.photo(act, CameraInEvent.HEAD_PHOTO, true);
                if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
            }

            @Override
            public void save() {
                ImageUtils.viewSaveToImage(act, mB.layout);
                showToast("保存成功");
            }
        });
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        cameraBottomFrg.show(getChildFragmentManager(), "dialog");
    }
}
