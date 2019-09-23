package com.yc.mema.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.dingmouren.layoutmanagergroup.CustomVideoView;
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yc.mema.R;
import com.yc.mema.adapter.CommentAdapter;
import com.yc.mema.adapter.VideoAdapter;
import com.yc.mema.base.BaseActivity;
import com.yc.mema.base.BaseFragment;
import com.yc.mema.bean.DataBean;
import com.yc.mema.controller.UIHelper;
import com.yc.mema.databinding.FVideoBinding;
import com.yc.mema.event.CollectionDelInEvent;
import com.yc.mema.event.VideoDelInEvent;
import com.yc.mema.impl.VideoContract;
import com.yc.mema.presenter.VideoPresenter;
import com.yc.mema.utils.Constants;
import com.yc.mema.view.bottomFrg.CommentBottomFrg;
import com.yc.mema.view.bottomFrg.DelBottomFrg;
import com.yc.mema.view.bottomFrg.ReportBottomFrg;
import com.yc.mema.weight.LinearDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Type;
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
    private CustomVideoView videoView;
    private AppCompatTextView tvCommentTitle;
    private int type = 0;//0 正常视频 1收藏 2我的生日趴 3消息进来
    public static final int NORMAL_VIDEO = 0;
    public static final int COLL_VIDEO = 1;
    public static final int MY_VIDEO = 2;
    public static final int MSG_VIDEO = 3;
    private int position;//收藏，我的生日趴要索引的地方
    private AppCompatTextView tvAdaComment;
    private AppCompatImageView ivPlayImg;
    private ImageView imgPlay;

    public static VideoFrg newInstance() {
        Bundle args = new Bundle();
        VideoFrg fragment = new VideoFrg();
        fragment.setArguments(args);
        return fragment;
    }


    private List<DataBean> listBean = new ArrayList<>();
    private VideoAdapter adapter;

    private List<DataBean> listComment = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private RecyclerView rvComment;
    private TwinklingRefreshLayout refreshLayoutComment;
    private int pagerNumberComment = 1;
    private CommentBottomFrg commentBottomFrg;
    private ReportBottomFrg reportBottomFrg;
    private DelBottomFrg delBottomFrg;

    @Override
    public void initPresenter() {
        mPresenter.init(this);
    }

    @Override
    protected void initParms(Bundle bundle) {
        type = bundle.getInt("isVideoType");
        Type type = new TypeToken<ArrayList<DataBean>>() {}.getType();
        if (this.type != NORMAL_VIDEO) {
            listBean = new Gson().fromJson(bundle.getString("list"), type);
        }
        position = bundle.getInt("position");
    }

    @Override
    protected int bindLayout() {
        return R.layout.f_video;
    }

    @Override
    protected void initView(View view) {
        setSwipeBackEnable(false);
        setSofia(false);
        setVideoReport();
        LinearLayout bottomSheet = view.findViewById(R.id.bottom_sheet);
        rvComment = view.findViewById(R.id.rv_comment);
//        refreshLayoutComment = view.findViewById(R.id.refreshLayout1);
        view.findViewById(R.id.tv_points).setOnClickListener(this);
        tvCommentTitle = view.findViewById(R.id.tv_comment_title);
        tvCommentTitle.setOnClickListener(this);
        setBottomSheet(bottomSheet);
        initComment();
        initVideo();
        EventBus.getDefault().register(this);
    }

    private void setBottomSheet(LinearLayout bottomSheet) {
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
    }

    private void setVideoReport() {
        if (type != NORMAL_VIDEO && type != MSG_VIDEO) {
            mB.fyClose.setOnClickListener(this);
            mB.fyLayout.setOnClickListener(this);
            mB.fyClose.setVisibility(View.VISIBLE);
            mB.fyLayout.setVisibility(View.VISIBLE);
            if (type == COLL_VIDEO) {
                mB.ivSandian.setVisibility(View.VISIBLE);
            } else {
                mB.tvDel.setVisibility(View.VISIBLE);
            }
            reportBottomFrg = new ReportBottomFrg();
            reportBottomFrg.setComplaintListener(() -> UIHelper.startReportNewsFrg(VideoFrg.this, listBean.get(mPosition).getVideoId(), Constants.CAUSES_VIDEO_COMPLAINTS));
            delBottomFrg = new DelBottomFrg();
            delBottomFrg.setComplaintListener(() -> mPresenter.onDelVideo(listBean.get(mPosition).getVideoId()));
        }
    }

    private void initVideo() {
        if (adapter == null) {
            adapter = new VideoAdapter(act, this, listBean);
        }
        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(act, OrientationHelper.VERTICAL);
        mB.recyclerView.setLayoutManager(layoutManager);
        mB.recyclerView.setAdapter(adapter);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
//                LogUtils.e(position);
                listComment.clear();
                mPosition = position;
                playVideo(0);
            }
        });

        showLoadDataing();
        if (type == NORMAL_VIDEO) {
            mB.refreshLayout.startRefresh();
            setRefreshLayout(mB.refreshLayout, new RefreshListenerAdapter() {
                @Override
                public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                    mPresenter.onRequest(pagerNumber = 1, type);
//                mB.refreshLayout.setEnableRefresh(false);
                }

                @Override
                public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                    super.onLoadMore(refreshLayout);
                    mPresenter.onRequest(pagerNumber += 1, type);
                }
            });
        } else {
            setData(listBean);
        }

        adapter.setOnClickListener(new VideoAdapter.OnClickListener() {
            @Override
            public void collection(String id, int i, int position, AppCompatImageView iv_coll, AppCompatTextView tv_coll) {
                mPresenter.onVideoColl(id, i, position, iv_coll, tv_coll);
            }

            @Override
            public void follow(String id) {

            }

            @Override
            public void comment(AppCompatTextView tv_comment) {
                tvAdaComment = tv_comment;
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    if (listComment.size() == 0) {
                        mPresenter.onComment(listBean.get(mPosition).getVideoId(), pagerNumberComment = 1);
                    } else {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }
            }

            @Override
            public void zan(String id, int i, AppCompatImageView iv_zan, AppCompatTextView tv_zan) {
                mPresenter.onVideoZan(id, i, mPosition, iv_zan, tv_zan);
            }

            @Override
            public void onClick(AppCompatImageView iv_img) {
                if (videoView.isPlaying()){
                    videoView.pause();
                    iv_img.setVisibility(View.VISIBLE);
                }else {
                    videoView.start();
                    iv_img.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initComment() {
        if (commentAdapter == null) {
            commentAdapter = new CommentAdapter(act, this, listComment, 1);
        }
        setRecyclerViewType(rvComment);
        rvComment.addItemDecoration(new LinearDividerItemDecoration(act, DividerItemDecoration.VERTICAL, 2));
        rvComment.setAdapter(commentAdapter);
//        refreshLayoutComment.setEnableRefresh(false);
//        setRefreshLayout(refreshLayoutComment, new RefreshListenerAdapter() {
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                mPresenter.onComment(listBean.get(mPosition).getVideoId(), pagerNumberComment += 1);
//            }
//        });
        commentAdapter.setOnClickListener(new CommentAdapter.OnClickListener() {
            @Override
            public void onSecondComment(int position, String videoId, String discussId, String pUserId) {
                commentBottomFrg.onSecondComment(position, 2, videoId, discussId, pUserId);
                commentBottomFrg.show(getChildFragmentManager(), "dialog");
            }

            @Override
            public void onZan(int position, String discussId, int type) {
                mPresenter.onZan(position, discussId, type);
            }
        });
        commentBottomFrg = new CommentBottomFrg();
        commentBottomFrg.setOnCommentListener(new CommentBottomFrg.onCommentListener() {
            @Override
            public void onFirstComment(String text) {
                mPresenter.onFirstComment(listBean.get(mPosition).getVideoId(), text);
            }

            @Override
            public void onSecondComment(int position, String videoId, String discussId, String text, String pUserId) {
                mPresenter.onSecondComment(position, videoId, discussId, text, pUserId);
            }
        });
    }

    @Override
    public void setRefreshLayoutMode(int totalRow) {
        super.setRefreshLayoutMode(listBean.size(), totalRow, mB.refreshLayout);
//        super.setRefreshLayoutMode(listComment.size(), totalRow, refreshLayoutComment);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        super.setRefreshLayout(pagerNumber, mB.refreshLayout);
//        super.setRefreshLayout(pagerNumberComment, refreshLayoutComment);
    }

    @Override
    public void setData(Object data) {
        if (type == NORMAL_VIDEO) {
            List<DataBean> list = (List<DataBean>) data;
            if (pagerNumber == 1) {
                listBean.clear();
                mB.refreshLayout.finishRefreshing();
            } else {
                mB.refreshLayout.finishLoadmore();
            }
            listBean.addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
            mB.recyclerView.smoothScrollToPosition(position);
            new Handler().postDelayed(() -> hideLoading(), 500);
        }
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        if (videoView != null && videoView.isPlaying()) {
            videoView.pause();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (videoView != null) {
//            videoView.start();
            imgPlay.animate().alpha(1f).start();
            imgPlay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setComment(List<DataBean> list) {
        if (pagerNumberComment == 1) {
            listComment.clear();
//            refreshLayoutComment.finishRefreshing();
        } else {
//            refreshLayoutComment.finishLoadmore();
        }
        listComment.addAll(list);
        commentAdapter.notifyDataSetChanged();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        tvCommentTitle.setText(listComment.size() + "条评论");
    }

    @Override
    public void onZan(int position, int type) {
        DataBean bean = listComment.get(position);
        bean.setIsTrue(type);
        bean.setPraiseCount(type == 0 ? bean.getPraiseCount() - 1 : bean.getPraiseCount() + 1);
        commentAdapter.notifyItemChanged(position);
    }

    @Override
    public void setFirstComment(DataBean result) {
        listComment.add(0, result);
        commentAdapter.notifyDataSetChanged();
        tvCommentTitle.setText(listComment.size() + "条评论");

        DataBean dataBean = listBean.get(mPosition);
        dataBean.setDiscuss(dataBean.getDiscuss() + 1);
//        adapter.notifyItemChanged(mPosition);
        tvAdaComment.setText(listComment.size() + "");
    }

    @Override
    public void setVideoZan(int position, int type, AppCompatImageView ivZan, AppCompatTextView tvZan) {
        DataBean bean = listBean.get(position);
        bean.setPraiseCount(type == 0 ? bean.getPraiseCount() - 1 : bean.getPraiseCount() + 1);
        bean.setpIsTrue(type);
//        adapter.notifyItemChanged(position);
        tvZan.setText(bean.getPraiseCount() + "");
        ivZan.setBackgroundResource(bean.getpIsTrue() == 0 ? R.mipmap.xihuan_1 : R.mipmap.xihuan_2);
    }

    @Override
    public void setVideoColl(int position, int type, AppCompatImageView iv_coll, AppCompatTextView tv_coll) {
        DataBean bean = listBean.get(position);
        bean.setcIsTrue(type);
//        adapter.notifyItemChanged(position);
        iv_coll.setBackgroundResource(bean.getcIsTrue() == 0 ? R.mipmap.shoucang_1 : R.mipmap.shoucan_2);
    }

    @Override
    public void setSecondComment(int position, DataBean result) {
        DataBean bean = listComment.get(position);
        List<DataBean> list = bean.getList();
        list.add(0, result);
        bean.setList(list);
//        commentAdapter.notifyItemInserted(position);
        commentAdapter.notifyItemChanged(position);
//        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void setDelVideo() {
        listBean.remove(mPosition);
        adapter.notifyItemRemoved(mPosition);
        adapter.notifyItemChanged(mPosition);
        EventBus.getDefault().post(new VideoDelInEvent(mPosition));
        mPosition = 0;
        if (listBean.size() == 0) {
            act.finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment_title:
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.tv_points:
                if (!((BaseActivity) act).isLogin()) return;
                commentBottomFrg.show(getChildFragmentManager(), "dialog");
                break;
            case R.id.fy_close:
                act.finish();
                break;
            case R.id.fy_layout:
                if (type == COLL_VIDEO) {
                    reportBottomFrg.show(getChildFragmentManager(), "dialog");
                } else {
                    delBottomFrg.show(getChildFragmentManager(), "dialog");
                }
                break;
        }
    }

    private void playVideo(int position) {
        View itemView = mB.recyclerView.getChildAt(0);
        videoView = itemView.findViewById(R.id.video_view);
        imgPlay = itemView.findViewById(R.id.iv_play);
        final ImageView imgThumb = itemView.findViewById(R.id.iv_img);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.start();
        videoView.setOnInfoListener((mp, what, extra) -> {
            mediaPlayer[0] = mp;
            mp.setLooping(true);
            imgThumb.animate().alpha(0).setDuration(200).start();
            return false;
        });
        videoView.setOnPreparedListener(mediaPlayer1 -> {
            if (!isSupportVisible()){
                videoView.pause();
            }
            LogUtils.e("setOnPreparedListener");
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    imgPlay.animate().alpha(1f).start();
                    videoView.pause();
                    isPlaying = false;
                } else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;
                }
            }
        });
        itemView.findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()) {
                    imgPlay.animate().alpha(1f).start();
                    videoView.pause();
                    isPlaying = false;
                } else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;
                }
            }
        });
    }

    private void releaseVideo(int index) {
        View itemView = mB.recyclerView.getChildAt(index);
        final CustomVideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.iv_img);
        final ImageView imgPlay = itemView.findViewById(R.id.iv_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }

    @Subscribe
    public void onMainCollectionDelInEvent(CollectionDelInEvent event) {
        for (String id : event.ids.split(",")) {
            for (DataBean bean : listBean) {
                if (id.equals(bean.getVideoId())) {
                    bean.setcIsTrue(0);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

}
