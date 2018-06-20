package com.oubowu.ipanda.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.LazyFragment;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.base.LiveVideo;
import com.oubowu.ipanda.bean.pandalive.LiveTab;
import com.oubowu.ipanda.bean.pandalive.MultipleLive;
import com.oubowu.ipanda.bean.pandalive.WatchTalk;
import com.oubowu.ipanda.callback.EventListenerAdapter;
import com.oubowu.ipanda.callback.VideoCallback;
import com.oubowu.ipanda.databinding.FragmentPandaLiveSubBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FlexImageAdapter;
import com.oubowu.ipanda.ui.widget.VideoImageView;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.GlideConfig;
import com.oubowu.ipanda.util.ToastUtil;
import com.oubowu.ipanda.viewmodel.PandaLiveSubViewModel;
import com.oubowu.ipanda.viewmodel.VideoViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import master.flame.danmaku.danmaku.util.SystemClock;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PandaLiveSubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PandaLiveSubFragment extends LazyFragment implements Injectable {

    private static final String NAME = "name";
    private static final String URL = "url";
    private static final String PADDING_TOP = "paddingTop";

    private String mName;
    private String mUrl;
    private int mPaddingTop;

    @Inject
    ViewModelProvider.Factory mFactory;

    private FragmentPandaLiveSubBinding mBinding;

    private Context mContext;

    private Disposable mDisposable;

    private VideoViewModel mVideoViewModel;

    public PandaLiveSubFragment() {
    }

    public static PandaLiveSubFragment newInstance(String name, String url, int paddingTop) {
        PandaLiveSubFragment fragment = new PandaLiveSubFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(URL, url);
        args.putInt(PADDING_TOP, paddingTop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(NAME);
            mUrl = getArguments().getString(URL);
            mPaddingTop = getArguments().getInt(PADDING_TOP);
        }
    }

    @Override
    protected @NonNull
    View inflateView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_panda_live_sub, container, false);

        mBinding.videoView.getTitleTextView().setVisibility(View.GONE);
        mBinding.videoView.getBackButton().setVisibility(View.GONE);
        mBinding.videoView.getFullscreenButton().setVisibility(View.GONE);
        mBinding.videoView.findViewById(com.shuyu.gsyvideoplayer.R.id.bottom_progressbar).setAlpha(0);
        mBinding.videoView.setIsTouchWiget(true);

        mBinding.liveDescArrow.setTag(-1, false);

        mBinding.space.getLayoutParams().height = mPaddingTop;

        return mBinding.getRoot();

    }

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作.
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible, boolean isFirstInit) {
        if (isVisible && !isFirstInit) {
            Log.e("PandaLiveSubFragment", mName + " 可见并且没有初始化过，网络请求成功");

            PandaLiveSubViewModel pandaLiveSubViewModel = ViewModelProviders.of(this, mFactory).get(PandaLiveSubViewModel.class);

            mVideoViewModel = ViewModelProviders.of(this, mFactory).get(VideoViewModel.class);

            pandaLiveSubViewModel.getLiveTab(mUrl).observe(this, new ObserverImpl<LiveTab>() {
                @Override
                protected void onSuccess(@NonNull LiveTab data) {

                    mIsFirstInit = true;

                    if (CommonUtil.isNotEmpty(data.live)) {

                        LiveTab.LiveBean liveBean = data.live.get(0);

                        mBinding.setLiveBean(liveBean);

                        setArrowEvent();

                        getLiveVideo(liveBean);
                    }

                    mBinding.flexImageLayout.setVisibility(View.VISIBLE);
                    if (CommonUtil.isNotEmpty(data.bookmark.multiple) && mBinding.flexImageLayout.getTag() == null) {
                        LiveTab.BookmarkBean.MultipleBean multipleBean = data.bookmark.multiple.get(0);
                        getMultipleLive(multipleBean, pandaLiveSubViewModel);
                    }

                    getLiveWatchTalk(data, pandaLiveSubViewModel);

                }

            });

        } else if (isVisible && isFirstInit) {
            Log.e("PandaLiveSubFragment", mName + " 可见并且初始化过，不做网络请求");
            //mBinding.videoView.onVideoResume();
        } else if (!isVisible) {
            mBinding.videoView.onVideoPause();
        }
    }

    private void getLiveWatchTalk(@NonNull LiveTab data, PandaLiveSubViewModel pandaLiveSubViewModel) {
        mBinding.videoView.setStandardVideoAllCallBack(new VideoCallback() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                if (CommonUtil.isNotEmpty(data.bookmark.watchTalk)) {
                    LiveTab.BookmarkBean.WatchTalkBean watchTalkBean = data.bookmark.watchTalk.get(0);
                    pandaLiveSubViewModel.getLiveWatchTalk(40, 0, watchTalkBean.url).observe(PandaLiveSubFragment.this, new ObserverImpl<WatchTalk>() {
                        @Override
                        protected void onSuccess(@NonNull WatchTalk data) {
                            addDanmu(data, new int[]{0});
                        }
                    });
                }
            }

            @Override
            public void onClickResume(String url, Object... objects) {
                super.onClickResume(url, objects);
                mBinding.videoView.onDanmukuResume();
            }

            @Override
            public void onPlayError(String url, Object... objects) {
                super.onPlayError(url, objects);
                ToastUtil.showErrorMsg("播放视频异常");
            }
        });
    }

    private void getMultipleLive(LiveTab.BookmarkBean.MultipleBean multipleBean, PandaLiveSubViewModel pandaLiveSubViewModel) {
        pandaLiveSubViewModel.getMultipleLive(multipleBean.url).observe(PandaLiveSubFragment.this, new ObserverImpl<List<MultipleLive>>() {
            @Override
            protected void onSuccess(@NonNull List<MultipleLive> data) {
                mBinding.flexImageLayout.setAdapter(new FlexImageAdapter<MultipleLive>(data) {

                    @Override
                    protected int getItemLayoutId() {
                        return R.layout.item_host_video_grid;
                    }

                    @SuppressWarnings("unchecked")
                    @Override
                    protected void initView(View view, int p, MultipleLive item) {
                        VideoImageView videoImageView = view.findViewById(R.id.video_image_view);
                        videoImageView.setInfo("Live", item.title, item.daytime);

                        Glide.with(view.getContext()).load(item.image).apply(GlideConfig.getInstance()).into(videoImageView);
                        view.setTag(-2, item.id);
                        view.setTag(-1, item.title);

                        view.setOnClickListener(v -> {

                            // Log.e("PandaLiveSubFragment", "233行-initView(): " + (String) view.getTag(-1));

                            mBinding.liveDescTitle.setText(String.format("%s%s", getString(R.string.live_now), (String) view.getTag(-1)));

                            getLiveVideo(view);

                            if (getParentFragment() instanceof PandaLiveFragment) {
                                View root = getParentFragment().getView();
                                if (root != null && root instanceof CoordinatorLayout) {
                                    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) root;
                                    final int childCount = coordinatorLayout.getChildCount();
                                    for (int i = 0; i < childCount; i++) {
                                        final View child = coordinatorLayout.getChildAt(i);
                                        if (child.getVisibility() == View.GONE) {
                                            // If the child is GONE, skip...
                                            continue;
                                        }
                                        final CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                                        final CoordinatorLayout.Behavior viewBehavior = lp.getBehavior();
                                        if (viewBehavior != null) {
                                            viewBehavior.onNestedFling(coordinatorLayout, child, root.findViewById(R.id.view_pager), 0, -10000, false);
                                        }
                                    }
                                }
                            }

                            mBinding.scrollView.fling(0);
                            mBinding.scrollView.smoothScrollTo(0, 0);


                        });

                    }

                });
                mBinding.flexImageLayout.setTag(true);
            }
        });
    }

    private void getLiveVideo(View view) {
        mVideoViewModel.getLiveVideo((String) view.getTag(-2)).observe(PandaLiveSubFragment.this, new ObserverImpl<LiveVideo>() {
            @Override
            protected void onSuccess(@NonNull LiveVideo data) {
                mBinding.videoView.setUp(data.hls_url.hls1, false, (String) view.getTag(-1));
                mBinding.videoView.startPlayLogic();
            }
        });
    }

    private void getLiveVideo(LiveTab.LiveBean liveBean) {
        mVideoViewModel.getLiveVideo(liveBean.id).observe(PandaLiveSubFragment.this, new ObserverImpl<LiveVideo>() {
            @Override
            protected void onSuccess(@NonNull LiveVideo data) {
                mBinding.videoView.setUp(data.hls_url.hls1, false, liveBean.title);
                mBinding.videoView.startPlayLogic();
            }
        });
    }

    private void setArrowEvent() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mBinding.liveDescArrow.getRotation(), mBinding.liveDescArrow.getRotation() + 180).setDuration(250);

        mBinding.setEvent(new EventListenerAdapter() {
            @Override
            public void clickArrow(View v) {
                super.clickArrow(v);
                boolean extend = (boolean) mBinding.liveDescArrow.getTag(-1);

                mBinding.liveDescArrow.setClickable(false);

                valueAnimator.setFloatValues(mBinding.liveDescArrow.getRotation(), mBinding.liveDescArrow.getRotation() + 180);

                valueAnimator.addUpdateListener(animation -> {
                    mBinding.liveDescArrow.setRotation((Float) animation.getAnimatedValue());
                    float fraction = animation.getAnimatedFraction();

                    if (!extend) {
                        mBinding.liveDesc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fraction * 14);
                    } else {
                        mBinding.liveDesc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (1 - fraction) * 14);
                    }
                });

                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mBinding.liveDescArrow.setClickable(true);
                        valueAnimator.removeAllListeners();
                        valueAnimator.removeAllUpdateListeners();
                        mBinding.liveDescArrow.setTag(-1, !extend);
                    }
                });

                valueAnimator.start();
            }
        });
    }

    private void addDanmu(WatchTalk mWatchTalk, int[] mStartIndex) {
        mDisposable = Observable //
                .interval(0, 7, TimeUnit.SECONDS) //
                .observeOn(Schedulers.single()) //
                .subscribe(aLong -> {
                    int end = (mStartIndex[0] + 5) > mWatchTalk.data.content.size() ? (mWatchTalk.data.content.size()) : (mStartIndex[0] + 5);
                    for (int j = mStartIndex[0]; j < end; j++) {
                        mBinding.videoView.addDanmaku(true, mWatchTalk.data.content.get(j).message);
                        SystemClock.sleep(20);
                    }
                    if (end == mWatchTalk.data.content.size()) {
                        mDisposable.dispose();
                        return;
                    }
                    mStartIndex[0] += 5;
                });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // 取消所有异步任务
        mContext = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.videoView.release();
        mBinding.danmakuView.release();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.videoView.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFragmentVisible || (getParentFragment() != null && !getParentFragment().isVisible())) {
            return;
        }
        //mBinding.videoView.onVideoResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (mBinding == null || mBinding.videoView == null) {
            return;
        }
        if (!mIsFragmentVisible) {
            return;
        }
        if (hidden) {
            mBinding.videoView.onVideoPause();
        } else {
            //mBinding.videoView.onVideoResume();
        }
    }

}
