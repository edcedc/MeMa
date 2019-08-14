package com.yc.mema.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.adapter.ImageAdapter;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FReleaseBinding;
import com.yc.mema.impl.ReleaseContract;
import com.yc.mema.presenter.ReleasePresenter;
import com.yc.mema.view.NewsDescFrg;
import com.yc.mema.weight.FullyGridLayoutManager;
import com.yc.mema.weight.PictureSelectorTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/31
 * Time: 16:04
 * 发布
 */
public class ReleaseAct extends BaseActivity<ReleasePresenter, FReleaseBinding> implements ReleaseContract.View, View.OnClickListener {

    private ImageAdapter imageAdapter;
    private List<LocalMedia> localMediaList = new ArrayList<>();

    @Override
    protected void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_release;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.release));
        mB.btSubmit.setOnClickListener(this);
        imageAdapter = new ImageAdapter(act, () -> {
            imageAdapter.setSelectMax(1);
            PictureSelectorTool.PictureSelectorVideo(act);
        });
        mB.recyclerView.setLayoutManager(new FullyGridLayoutManager(act, 4, GridLayoutManager.VERTICAL, false));
        mB.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mB.recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener((position, v) -> {
            LocalMedia media = localMediaList.get(position);
            String path = media.getPath();
            PictureSelectorTool.PictureMediaType(act, localMediaList, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    localMediaList.addAll(PictureSelector.obtainMultipleResult(data));
                    imageAdapter.setList(localMediaList);
                    imageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                mPresenter.onRelease(mB.etText.getText().toString(), localMediaList.get(0).getPath(), localMediaList.get(0).getCompressPath());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
