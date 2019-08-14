package com.yc.mema.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.adapter.ImageAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FReportBinding;
import com.yc.mema.databinding.FReportNewsBinding;
import com.yc.mema.event.CameraInEvent;
import com.yc.mema.impl.ReportNewsContract;
import com.yc.mema.presenter.ReportNewsPresenter;
import com.yc.mema.utils.Constants;
import com.yc.mema.weight.FullyGridLayoutManager;
import com.yc.mema.weight.PictureSelectorTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/8/7
 * Time: 18:03
 */
public class ReportFrg extends BaseFragment<ReportNewsPresenter, FReportBinding> implements ReportNewsContract.View, View.OnClickListener {

    private String id;
    private int type;
    private String soId;
    private String soName;
    public static boolean isFinish = false;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    private ImageAdapter imageAdapter;
    private List<LocalMedia> localMediaList = new ArrayList<>();

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
        type = bundle.getInt("type");
        soId = bundle.getString("soId");
        soName = bundle.getString("soName");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_report;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.complaint));
        EventBus.getDefault().register(this);
        mB.btSubmit.setOnClickListener(this);
        imageAdapter = new ImageAdapter(act, () -> {
            imageAdapter.setSelectMax(9);
            PictureSelectorTool.PictureSelectorImage(act, localMediaList, CameraInEvent.HEAD_CAMEAR);
        });
        mB.recyclerView.setLayoutManager(new FullyGridLayoutManager(act, 3, GridLayoutManager.VERTICAL, false));
        mB.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mB.recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener((position, v) -> {
            LocalMedia media = localMediaList.get(position);
            String path = media.getPath();
            PictureSelectorTool.PictureMediaType(act, localMediaList, position);
        });
        mB.etText.setText(soName);
        mB.tvContent.setText(soName.length() + "/200");
        mB.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 200) {
                    return;
                }
                mB.tvContent.setText(editable.length() + "/200");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_submit:
                switch (type){
                    case Constants .CAUSES_WELFARE_COMPLAINTS:
                        mPresenter.onGiftReport(id, soId, localMediaList, mB.etText.getText().toString());
                        break;
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainThreadInEvent(CameraInEvent event) {
        localMediaList.clear();
        localMediaList.addAll(PictureSelector.obtainMultipleResult((Intent) event.getObject()));
        imageAdapter.setList(localMediaList);
        imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setComplain(DataBean result) {

    }

    @Override
    public void setReport() {
        isFinish = true;
        pop();
    }

    @Override
    public void setData(List<DataBean> list) {

    }

}
