package com.yc.mema.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yc.mema.R;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseRecyclerviewAdapter;
import com.yc.mema.bean.DataBean;
import com.yc.mema.utils.OneGlideImageLoader;
import com.yc.mema.weight.LinearDividerItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.DefaultTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/9/28
 * Time: 21:50
 */
public class HomeClassifyAdapter extends BaseRecyclerviewAdapter<DataBean> {

    public HomeClassifyAdapter(Context act, List<DataBean> listBean) {
        super(act, listBean);
    }

    @Override
    protected void onBindViewHolde(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        DataBean bean = listBean.get(position);
        viewHolder.tv_title.setText(bean.getTitle());

        List<DataBean> list = bean.getList();
        if (list != null && list.size() != 0){
            list.addAll(list);
            list.addAll(list);
            list.addAll(list);
            list.addAll(list);
            viewHolder.recyclerView.setVisibility(View.VISIBLE);
            TeaAdapter adapter = new TeaAdapter(act, list);
            LinearLayoutManager tlayoutManager = new LinearLayoutManager(act);
            tlayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.recyclerView.setLayoutManager(tlayoutManager);
            viewHolder.recyclerView.setHasFixedSize(true);
            viewHolder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            viewHolder.recyclerView.setAdapter(adapter);
            viewHolder.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.HORIZONTAL, 20, Color.parseColor("#ffffff")));
        }else {
            viewHolder.recyclerView.setVisibility(View.GONE);
        }

        List<DataBean> bannerList = bean.getBannerList();
        if (bannerList != null && bannerList.size() != 0){
            viewHolder.banner.setVisibility(View.VISIBLE);
            viewHolder.banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
            viewHolder.banner.setImages(bannerList)
                    .setImageLoader(new OneGlideImageLoader())
                    .setOnBannerListener(position1 -> {
                        DataBean bean1 = bannerList.get(position1);
                    })
                    .setBannerAnimation(DefaultTransformer.class)
                    .start();
        }else {
            viewHolder.banner.setVisibility(View.GONE);
        }

    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolde(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.i_home_class, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView tv_title;
        RecyclerView recyclerView;
        Banner banner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            banner = itemView.findViewById(R.id.banner);
        }
    }

}
