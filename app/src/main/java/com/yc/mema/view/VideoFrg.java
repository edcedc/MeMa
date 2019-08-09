package com.yc.mema.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.TikTokController;
import com.dueeeke.videoplayer.OnViewPagerListener;
import com.dueeeke.videoplayer.ViewPagerLayoutManager;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.VideoView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.bean.VideoBean;
import com.yc.mema.adapter.CommentAdapter;
import com.yc.mema.adapter.VideoAdapter;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.databinding.FVideoBinding;
import com.yc.mema.impl.VideoContract;
import com.yc.mema.presenter.VideoPresenter;
import com.yc.mema.view.bottomFrg.CommentBottomFrg;
import com.yc.mema.view.bottomFrg.ReportBottomFrg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/7/29
 * Time: 19:16
 */
public class VideoFrg extends BaseFragment<VideoPresenter, FVideoBinding> implements VideoContract.View, View.OnClickListener {

    private int mPosition;
    private BottomSheetBehavior behavior;
    private boolean isState = false;

    public static VideoFrg newInstance() {
        Bundle args = new Bundle();
        VideoFrg fragment = new VideoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    private VideoView mVideoView;
    private TikTokController mTikTokController;
    private int mCurrentPosition;

    private List<DataBean> listBean = new ArrayList<>();
    private VideoAdapter adapter;
    private List<VideoBean> mVideoList;

    private List<DataBean> listComment = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private RecyclerView rvComment;
    private TwinklingRefreshLayout refreshLayoutComment;
    private int pagerNumberComment = 1;
    private CommentBottomFrg commentBottomFrg;
    private ReportBottomFrg reportBottomFrg;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        isState = bundle.getBoolean("isState");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        if (isState){
            setSofia(false);
            mB.fyClose.setOnClickListener(this);
            mB.fyLayout.setOnClickListener(this);
            mB.fyClose.setVisibility(View.VISIBLE);
            mB.fyLayout.setVisibility(View.VISIBLE);
            reportBottomFrg = new ReportBottomFrg();
        }

        LinearLayout bottomSheet = view.findViewById(R.id.bottom_sheet);
        rvComment = view.findViewById(R.id.rv_comment);
        refreshLayoutComment = view.findViewById(R.id.refreshLayout1);
        view.findViewById(R.id.tv_points).setOnClickListener(this);
        view.findViewById(R.id.tv_comment_title).setOnClickListener(this);
        ViewGroup.LayoutParams params = bottomSheet.getLayoutParams();
        params.height = ScreenUtils.getScreenHeight() - ScreenUtils.getScreenHeight() / 3;
        bottomSheet.setLayoutParams(params);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        });
        if (commentAdapter == null){
            commentAdapter = new CommentAdapter(act, this, listComment);
        }
        setRecyclerViewType(rvComment);
        rvComment.setAdapter(commentAdapter);
        mPresenter.onComment(pagerNumberComment = 1);
        refreshLayoutComment.setEnableRefresh(false);
        setRefreshLayout(refreshLayoutComment, new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onComment(pagerNumberComment += 1);
            }
        });
        commentBottomFrg = new CommentBottomFrg();

        mVideoList = getTikTokVideoList();
        mVideoView = new VideoView(act);
        mVideoView.setLooping(true);
        mTikTokController = new TikTokController(act);
        mVideoView.setVideoController(mTikTokController);
        if (adapter == null) {
            adapter = new VideoAdapter(act, this, listBean);
        }
        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(act, OrientationHelper.VERTICAL);
        mB.recyclerView.setLayoutManager(layoutManager);
        mB.recyclerView.setAdapter(adapter);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第一条
                startPlay(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (mCurrentPosition == position) {
                    mVideoView.release();
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                mPosition = position;
                if (mCurrentPosition == position) return;
                startPlay(position);
                mCurrentPosition = position;
            }
        });
        showLoadDataing();
        mB.refreshLayout.startRefresh();
        setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                mPresenter.onRequest(pagerNumber = 1);
                mB.refreshLayout.setEnableRefresh(false);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                mPresenter.onRequest(pagerNumber += 1);
            }
        });
        adapter.setOnClickListener(new VideoAdapter.OnClickListener() {
            @Override
            public void play(boolean isPlay, int position) {
                mPosition = position;
                if (isPlay) {
                    mVideoView.start();
                } else {
                    mVideoView.pause();
                }
            }

            @Override
            public void collection(String id) {

            }

            @Override
            public void follow(String id) {

            }

            @Override
            public void comment() {
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void zan(String id) {

            }

        });
        mVideoView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
            }

            @Override
            public void onPlayStateChanged(int playState) {
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                mVideoView.setPlayImg(playState == 4 ? View.VISIBLE : View.GONE);
            }
        });
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
//        List<DataBean> list = (List<DataBean>) data;

        List<DataBean> list = new ArrayList<>();
        List<VideoBean> videoList = getTikTokVideoList();
        for (VideoBean videoBean : videoList) {
            DataBean bean = new DataBean();
            bean.setTitle(videoBean.getTitle());
            bean.setImage(videoBean.getThumb());
            list.add(bean);
        }

        if (pagerNumber == 1) {
            listBean.clear();
            mB.refreshLayout.finishRefreshing();
        } else {
            mB.refreshLayout.finishLoadmore();
        }
        listBean.addAll(list);
        adapter.notifyDataSetChanged();

    }

    private void startPlay(int position) {
        View itemView = mB.recyclerView.getChildAt(0);
        FrameLayout frameLayout = itemView.findViewById(R.id.container);
        Glide.with(this)
                .load(listBean.get(position).getImage())
                .into(mTikTokController.getThumb());
        ViewParent parent = mVideoView.getParent();
        if (parent instanceof FrameLayout) {
            ((FrameLayout) parent).removeView(mVideoView);
        }
        frameLayout.addView(mVideoView);
        mVideoView.setUrl(mVideoList.get(position).getUrl());
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
        mVideoView.start();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        mVideoView.pause();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mVideoView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mVideoView.release();
    }

    /**
     * 抖音演示数据
     */
    public static List<VideoBean> getTikTokVideoList() {
        List<VideoBean> videoList = new ArrayList<>();
        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4c87000639ab0f21c285.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bea0014e31708ecb03e.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bb500130248a3bcdad0.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b8300007d1906573584.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b61000b6a4187626dda.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4c87000639ab0f21c285.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bea0014e31708ecb03e.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bb500130248a3bcdad0.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b8300007d1906573584.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b61000b6a4187626dda.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4c87000639ab0f21c285.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=97022dc18711411ead17e8dcb75bccd2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bea0014e31708ecb03e.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=374e166692ee4ebfae030ceae117a9d0&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p1.pstatp.com/large/4bb500130248a3bcdad0.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=8a55161f84cb4b6aab70cf9e84810ad2&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b8300007d1906573584.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=47a9d69fe7d94280a59e639f39e4b8f4&line=0&ratio=720p&media_type=4&vr_type=0"));

        videoList.add(new VideoBean("",
                "https://p9.pstatp.com/large/4b61000b6a4187626dda.jpeg",
                "https://aweme.snssdk.com/aweme/v1/play/?video_id=3fdb4876a7f34bad8fa957db4b5ed159&line=0&ratio=720p&media_type=4&vr_type=0"));
        return videoList;
    }

    @Override
    public void setComment(List<DataBean> list) {
        if (pagerNumberComment == 1) {
            listComment.clear();
            refreshLayoutComment.finishRefreshing();
        } else {
            refreshLayoutComment.finishLoadmore();
        }
        listComment.addAll(list);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_comment_title:
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.tv_points:
                commentBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
            case R.id.fy_close:
                act.finish();
                break;
            case R.id.fy_layout:
                reportBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
        }
    }
}
