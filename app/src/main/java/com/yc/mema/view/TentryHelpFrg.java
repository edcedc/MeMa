package com.yc.mema.view;

import android.os.Bundle;
import android.view.View;

import com.yc.mema.R;
import com.yc.mema.adapter.TentryHelpAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FTentryHelpBinding;
import com.yc.mema.impl.TentryContract;
import com.yc.mema.presenter.TentryPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 16:42
 * 商家入驻帮助
 */
public class TentryHelpFrg extends BaseFragment<TentryPresenter, FTentryHelpBinding> implements TentryContract.View {

    private List<DataBean> listBean1 = new ArrayList<>();
    private List<DataBean> listBean2 = new ArrayList<>();
    private List<DataBean> listBean3 = new ArrayList<>();
    private TentryHelpAdapter adapter1;
    private TentryHelpAdapter adapter2;
    private TentryHelpAdapter adapter3;


    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.f_tentry_help;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.help1));
        adapter1 = new TentryHelpAdapter(act, listBean1);
        mB.listView1.setAdapter(adapter1);
        adapter2 = new TentryHelpAdapter(act, listBean2);
        mB.listView2.setAdapter(adapter2);
        adapter3 = new TentryHelpAdapter(act, listBean3);
        mB.listView3.setAdapter(adapter3);

        mPresenter.onHelpList();
    }

    @Override
    public void setData(List<DataBean> list) {
        //「1么马开店说明 2热门问题 3常见问题」
        for (DataBean bean : list){
            switch (bean.getType()){
                case 1:
                    listBean1.add(bean);
                    break;
                case 2:
                    listBean2.add(bean);
                    break;
                default:
                    listBean3.add(bean);
                    break;
            }
        }
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }
}
