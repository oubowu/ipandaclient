package com.oubowu.ipanda.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.res.Configuration;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.base.LiveVideo;
import com.oubowu.ipanda.bean.chinalive.ChinaLiveDetail;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.callback.VideoCallback;
import com.oubowu.ipanda.databinding.ItemFragmentChinaLiveSubBinding;
import com.oubowu.ipanda.ui.widget.CoverVideoPlayer;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.viewmodel.VideoViewModel;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by Oubowu on 2018/2/23 16:54.
 */
public class ChinaLiveSubAdapter extends DataBoundListAdapter<ChinaLiveDetail, ItemFragmentChinaLiveSubBinding> {

    private static final String TAG = "ChinaLiveSubAdapter";

    private Fragment mFragment;
    private DataBindingComponent mDataBindingComponent;
    private VideoViewModel mVideoViewModel;

    private OrientationUtils mOrientationUtils;
    private StandardGSYVideoPlayer mCurPlayer;
    private boolean mIsPlay;
    private boolean mHasPlayed;

    public ChinaLiveSubAdapter(Fragment fragment, DataBindingComponent dataBindingComponent, VideoViewModel videoViewModel) {
        mFragment = fragment;
        mDataBindingComponent = dataBindingComponent;
        mVideoViewModel = videoViewModel;
    }

    @Override
    protected ItemFragmentChinaLiveSubBinding createBinding(ViewGroup parent, int viewType) {
        ItemFragmentChinaLiveSubBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_china_live_sub, parent, false, mDataBindingComponent);
        return binding;
    }

    @Override
    protected void bind(ItemFragmentChinaLiveSubBinding binding, int position, ChinaLiveDetail detail, int itemViewType) {

        binding.setDetail(detail);

        binding.liveDesc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, detail.isExpend ? 14 : 0);
        binding.liveDescArrow.setRotation(detail.isExpend ? 0 : 180);

        binding.setEvent(new EventListenerAdapter() {
            @Override
            public void clickArrow(View v) {
                playAnimation(binding, detail);
            }
        });

        CoverVideoPlayer player = binding.coverVideoPlayer;

        player.getStartButton().setOnClickListener(v -> {
            if (CommonUtil.isNotEmpty(player.getUrl())) {
                player.clickStartIcon();
                return;
            }
            mVideoViewModel.getLiveVideo(detail.id).observe(mFragment, liveVideoResource -> {
                if (liveVideoResource != null) {
                    switch (liveVideoResource.status) {
                        case SUCCESS:
                            LiveVideo liveVideo = liveVideoResource.data;
                            if (liveVideo != null) {
                                player.setUpLazy(liveVideo.hls_url.hls1, false, null, null, detail.title);
                                player.clickStartIcon();
                            }
                            break;
                        case ERROR:

                            break;
                        case LOADING:

                            break;
                    }
                }
            });
        });

        player.getFullscreenButton().setOnClickListener(v -> {
            resolveFullBtn(player);
        });

        player.getBackButton().setVisibility(View.GONE);
        player.findViewById(com.shuyu.gsyvideoplayer.R.id.progress).setVisibility(View.INVISIBLE);
        player.findViewById(com.shuyu.gsyvideoplayer.R.id.total).setVisibility(View.INVISIBLE);
        player.findViewById(com.shuyu.gsyvideoplayer.R.id.bottom_progressbar).setAlpha(0);

        player.setRotateViewAuto(false);
        player.setLockLand(false);
        player.setPlayTag(TAG);
        player.setShowFullAnimation(false);
        player.setIsTouchWiget(false);
        player.setNeedLockFull(true);
        player.setPlayPosition(position);

        player.loadCoverImage(detail.image, R.drawable.xxx);

        player.setStandardVideoAllCallBack(new VideoCallback() {

            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                if (!player.isIfCurrentIsFullscreen()) {
                    // 播放器不是全屏，需要静音
                    GSYVideoManager.instance().setNeedMute(true);
                }
                mCurPlayer = (StandardGSYVideoPlayer) objects[1];
                mIsPlay = true;
                if (getListNeedAutoLand()) {
                    //重力全屏工具类
                    initOrientationUtils(player);
                    ChinaLiveSubAdapter.this.onPrepared();
                }
            }

            @Override
            public void onQuitFullscreen(String url, Object... objects) {
                super.onQuitFullscreen(url, objects);
                GSYVideoManager.instance().setNeedMute(true);
                if (getListNeedAutoLand()) {
                    ChinaLiveSubAdapter.this.onQuitFullscreen();
                }
            }

            @Override
            public void onEnterFullscreen(String url, Object... objects) {
                super.onEnterFullscreen(url, objects);
                GSYVideoManager.instance().setNeedMute(false);
                player.getCurrentPlayer().getTitleTextView().setText((String) objects[0]);
            }

            @Override
            public void onAutoComplete(String url, Object... objects) {
                super.onAutoComplete(url, objects);
                mCurPlayer = null;
                mIsPlay = false;
                if (getListNeedAutoLand()) {
                    ChinaLiveSubAdapter.this.onAutoComplete();
                }
            }
        });

        binding.executePendingBindings();

    }

    private void resolveFullBtn(CoverVideoPlayer coverVideoPlayer) {
        if (mOrientationUtils != null) {
            resolveFull();
        }
        coverVideoPlayer.startWindowFullscreen(mFragment.getActivity(), false, true);
    }

    /**************************支持全屏重力全屏的部分**************************/

    /**
     * 列表时是否需要支持重力旋转
     *
     * @return 返回true为支持列表重力全屏
     */
    public boolean getListNeedAutoLand() {
        return true;
    }

    private void initOrientationUtils(CoverVideoPlayer player) {
        mOrientationUtils = new OrientationUtils(mFragment.getActivity(), player);
        //是否需要跟随系统旋转设置
        //orientationUtils.setRotateWithSystem(false);
        mOrientationUtils.setEnable(false);
    }

    private void resolveFull() {
        if (mOrientationUtils != null) {
            //直接横屏
            mOrientationUtils.resolveByClick();
        }
    }

    private void onQuitFullscreen() {
        if (mOrientationUtils != null) {
            mOrientationUtils.backToProtVideo();
        }
    }

    private void onAutoComplete() {
        if (mOrientationUtils != null) {
            mOrientationUtils.setEnable(false);
            mOrientationUtils.releaseListener();
            mOrientationUtils = null;
        }
        mIsPlay = false;
    }

    private void onPrepared() {
        if (mOrientationUtils == null) {
            return;
        }
        mHasPlayed = true;
        //开始播放了才能旋转和全屏
        mOrientationUtils.setEnable(true);
    }

    public void onConfigurationChanged(Activity activity, Configuration newConfig) {
        //如果旋转了就全屏
        if (mIsPlay && mCurPlayer != null && mOrientationUtils != null) {
            mCurPlayer.onConfigurationChanged(activity, newConfig, mOrientationUtils);
        }
    }

    public OrientationUtils getOrientationUtils() {
        return mOrientationUtils;
    }


    public void onBackPressed() {
        if (mOrientationUtils != null) {
            mOrientationUtils.backToProtVideo();
        }
    }

    public void onDestroy() {
        if (mIsPlay && mCurPlayer != null) {
            mCurPlayer.getCurrentPlayer().release();
        }
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
            mOrientationUtils = null;
        }
    }


    private void playAnimation(ItemFragmentChinaLiveSubBinding binding, ChinaLiveDetail detail) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(binding.liveDescArrow.getRotation(), binding.liveDescArrow.getRotation() + 180).setDuration(250);

        boolean extend = detail.isExpend;

        binding.liveDescArrow.setClickable(false);

        valueAnimator.setFloatValues(binding.liveDescArrow.getRotation(), binding.liveDescArrow.getRotation() + 180);

        valueAnimator.addUpdateListener(animation -> {
            binding.liveDescArrow.setRotation((Float) animation.getAnimatedValue());
            float fraction = animation.getAnimatedFraction();

            if (!extend) {
                binding.liveDesc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fraction * 14);
            } else {
                binding.liveDesc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (1 - fraction) * 14);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                binding.liveDescArrow.setClickable(true);
                valueAnimator.removeAllListeners();
                valueAnimator.removeAllUpdateListeners();
                detail.isExpend = !detail.isExpend;
                binding.executePendingBindings();
            }
        });

        valueAnimator.start();
    }

    @Override
    protected boolean areItemsTheSame(ChinaLiveDetail oldItem, ChinaLiveDetail newItem) {
        return CommonUtil.equals(oldItem.id, newItem.id);
    }

    @Override
    protected boolean areContentsTheSame(ChinaLiveDetail oldItem, ChinaLiveDetail newItem) {
        return CommonUtil.equals(oldItem.id, newItem.id);
    }


    public boolean hasPlayed() {
        return mHasPlayed;
    }
}
