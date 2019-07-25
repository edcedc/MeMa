package com.yc.mema.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.databinding.FHeadBinding;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.impl.InformationContract;
import com.yc.mema.presenter.InformationPresenter;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.view.bottomFrg.CameraBottomFrg;
import com.yc.mema.weight.PictureSelectorTool;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/24
 * Time: 20:18
 *  头像
 */
public class HeadFrg extends BaseFragment<InformationPresenter, FHeadBinding> implements InformationContract.View {

    private CameraBottomFrg cameraBottomFrg;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private String headPath;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_head;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.user_head), R.mipmap.y17);
        ViewGroup.LayoutParams params = mB.ivHead.getLayoutParams();
        params.width = ScreenUtils.getScreenWidth();
        params.height = params.width;
        mB.ivHead.setLayoutParams(params);

        if (cameraBottomFrg == null){
            cameraBottomFrg = new CameraBottomFrg(0);
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

            }
        });

        GlideLoadingUtils.load(act, "", mB.ivHead);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        if (cameraBottomFrg != null && cameraBottomFrg.isShowing())cameraBottomFrg.dismiss();
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        String path = localMediaList.get(0).getCompressPath();
        headPath = path;
        Glide.with(act).load(path).into(mB.ivHead);
        LogUtils.e(path);
    }

    @Override
    protected void setOnRightClickListener() {
        super.setOnRightClickListener();
        cameraBottomFrg.show(getChildFragmentManager(), "dialog");
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {

    }

    @Override
    public void setData(Object data) {

    }
}
