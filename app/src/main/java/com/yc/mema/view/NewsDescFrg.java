package com.yc.mema.view;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yc.mema.R;
import com.yc.mema.adapter.CommentAdapter;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.CloudApi;
import com.yc.mema.databinding.FNewsDescBinding;
import com.yc.mema.impl.NewsDescContract;
import com.yc.mema.presenter.NewsDescPresenter;
import com.yc.mema.utils.GlideLoadingUtils;
import com.yc.mema.view.bottomFrg.CommentBottomFrg;
import com.yc.mema.weight.LinearDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/28
 * Time: 23:26
 */
public class NewsDescFrg extends BaseFragment<NewsDescPresenter, FNewsDescBinding> implements NewsDescContract.View, View.OnClickListener {

    private List<DataBean> listBean = new ArrayList<>();
    private CommentAdapter adapter;

    private CommentBottomFrg commentBottomFrg;
    private String id;

    public static NewsDescFrg newInstance() {
        Bundle args = new Bundle();
        NewsDescFrg fragment = new NewsDescFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        id = bundle.getString("id");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_news_desc;
    }

    @Override
    protected void initView(View view) {
        setTitle(getString(R.string.news_desc));
        mB.tvPoints.setOnClickListener(this);
        commentBottomFrg = new CommentBottomFrg();
        commentBottomFrg.setOnCommentListener(new CommentBottomFrg.onCommentListener() {
            @Override
            public void onFirstComment(String text) {
                mPresenter.onFirstComment(id, text);
            }

            @Override
            public void onSecondComment(int position, String infoId, String discussId, String text, String pUserId) {
                mPresenter.onSecondComment(position, infoId, discussId, text, pUserId);
            }
        });
        if (adapter == null){
            adapter = new CommentAdapter(act, this, listBean);
        }
        setRecyclerViewType(mB.recyclerView);
        mB.recyclerView.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL,  2));
        mB.recyclerView.setAdapter(adapter);

        mPresenter.onInformation(id);
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(id, pagerNumber = 1);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(id, pagerNumber += 1);
            }
        });

        mB.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView var1, int var2, String var3, String var4) {
                mB.progressBar.setVisibility(View.GONE);
                ToastUtils.showShort("网页加载失败");
            }
        });
        //进度条
        mB.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mB.progressBar.setVisibility(View.GONE);
                    return;
                }
                mB.progressBar.setVisibility(View.VISIBLE);
                mB.progressBar.setProgress(newProgress);
            }
        });
        adapter.setOnClickListener(new CommentAdapter.OnClickListener() {
            @Override
            public void onSecondComment(int position, String infoId, String discussId, String pUserId) {
                commentBottomFrg.onSecondComment(position, 2, infoId, discussId, pUserId);
                commentBottomFrg.show(getChildFragmentManager(), "dialog");
            }

            @Override
            public void onZan(int position, String discussId, int type) {
                mPresenter.onZan(position, discussId, type);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!((BaseActivity)act).isLogin())return;
        switch (view.getId()){
            case R.id.tv_points:
                commentBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
        }
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
    }

    @Override
    public void setData(Object data) {
        List<DataBean> list = (List<DataBean>) data;
        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        mB.webView.removeAllViews();
        mB.webView.destroy();
        super.onDestroy();
    }

    @Override
    public void setInformation(DataBean bean) {
        mB.tvTitle.setText(bean.getTitle());
        mB.tvTime.setText(bean.getCreateTime().split(" ")[0]);
        mB.tvLike.setText(bean.getPraise() + "");
        mB.tvComment.setText(bean.getDiscuss() + "");
        List<DataBean> img = bean.getInformationImg();
        if (img != null && img.size() != 0){
            GlideLoadingUtils.load(act, CloudApi.SERVLET_IMG_URL + img.get(0).getAttachId(), mB.ivImg);
        }
        mB.webView.loadDataWithBaseURL(null, bean.getContext(), "text/html", "utf-8", null);
    }

    @Override
    public void firstComment(DataBean result) {
        listBean.add(0, result);
//        adapter.notifyItemInserted(0);
//        adapter.notifyItemChanged(0);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void secondComment(int position, DataBean result) {
        DataBean bean = listBean.get(position);
        List<DataBean> list = bean.getList();
        list.add(0, result);
        bean.setList(list);
//        adapter.notifyItemInserted(position);
//        adapter.notifyItemChanged(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onZan(int position, int type) {

    }
}