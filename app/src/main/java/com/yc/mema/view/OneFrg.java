package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.mema.R;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.base.BasePresenter;
import com.yc.mema.databinding.FOneBinding;
import com.yc.mema.utils.ShareTool;

import java.util.Map;

public class OneFrg extends BaseFragment<BasePresenter, FOneBinding> {

    public static OneFrg newInstance() {
        Bundle args = new Bundle();
        OneFrg fragment = new OneFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_one;
    }

    @Override
    protected void initView(View view) {
        setTitle("哈");
        mB.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareTool.getInstance(act).Authorization(new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        LogUtils.e(map);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
            }
        });
    }
}
