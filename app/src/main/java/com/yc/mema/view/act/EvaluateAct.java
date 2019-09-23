package com.yc.mema.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.yc.mema.R;
import com.yc.mema.adapter.ConfirmOrdersAdapter;
import com.yc.mema.adapter.ImageAdapter;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FEvaluateBinding;
import com.yc.mema.impl.EvaluateContract;
import com.yc.mema.presenter.EvaluatePresenter;
import com.yc.mema.weight.FullyGridLayoutManager;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.yc.mema.weight.PictureSelectorTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/12
 * Time: 17:14
 */
public class EvaluateAct extends BaseActivity<EvaluatePresenter, FEvaluateBinding> implements EvaluateContract.View, View.OnClickListener {

    private String id;
    private ImageAdapter imageAdapter;
    private List<LocalMedia> localMediaList = new ArrayList<>();
    private List<DataBean> listBean = new ArrayList<>();
    private ConfirmOrdersAdapter adapter;
    private DataBean bean;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        bean = new Gson().fromJson(bundle.getString("bean"), DataBean.class);
        id = bean.getOrderId();
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_evaluate;
    }

    @Override
    protected void initView() {
        setTitle(getString(R.string.order_evaluation));
        mB.btSubmit.setOnClickListener(this);
        mB.refreshLayout.setPureScrollModeOn();
        bean.setOrderNum(null);
        listBean.add(bean);
        if (adapter == null){
            adapter = new ConfirmOrdersAdapter(act, listBean);
        }
        setRecyclerViewType(mB.rvOrder, R.color.white);
        mB.rvOrder.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.rvOrder.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        imageAdapter = new ImageAdapter(act, () -> {
            imageAdapter.setSelectMax(9);
            PictureSelectorTool.PictureSelectorImage(act, localMediaList, PictureConfig.CHOOSE_REQUEST);
        });
        mB.recyclerView.setLayoutManager(new FullyGridLayoutManager(act, 3, GridLayoutManager.VERTICAL, false));
        mB.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mB.recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener((position, v) -> {
            LocalMedia media = localMediaList.get(position);
            PictureSelectorTool.PictureMediaType(act, localMediaList, position);
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
        switch (view.getId()){
            case R.id.bt_submit:
                mPresenter.onSubmit(bean.getGoodId(), bean.getOrderId(), mB.etText.getText().toString(), localMediaList, mB.ratingbar.getRating());
                break;
        }
    }
}
