package com.oubowu.ipanda.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.base.LiveVideo;
import com.oubowu.ipanda.bean.pandalive.LiveTab;
import com.oubowu.ipanda.databinding.FragmentPandaLiveSubBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.viewmodel.PandaLiveSubViewModel;
import com.oubowu.ipanda.viewmodel.VideoViewModel;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PandaLiveSubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PandaLiveSubFragment extends Fragment implements Injectable {

    private static final String NAME = "name";
    private static final String URL = "url";

    private String mName;
    private String mUrl;

    protected View mRootView;

    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean mIsFragmentVisible;
    //是否是第一次开启网络加载
    public boolean mIsFirstInit;

    @Inject
    ViewModelProvider.Factory mFactory;

    private FragmentPandaLiveSubBinding mBinding;
    private OrientationUtils mOrientationUtils;

    public PandaLiveSubFragment() {
    }

    public static PandaLiveSubFragment newInstance(String name, String url) {
        PandaLiveSubFragment fragment = new PandaLiveSubFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Fragment可见时，setUserVisibleHint()回调，其中isVisibleToUser=true。
     * 当前Fragment由可见到不可见或实例化时，setUserVisibleHint()回调，其中isVisibleToUser=false
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsFragmentVisible = isVisibleToUser;
        if (mRootView == null) {
            // 实例化的时候，mRootView为null，setUserVisibleHint是最优先跑的，直接返回，避免下面的逻辑
            return;
        }
        // 可见，并且没有加载过
        if (mIsFragmentVisible && !mIsFirstInit) {
            onFragmentVisibleChange(true, false);
        } else if (mIsFragmentVisible && mIsFirstInit) {
            // 可见，并且初始化过了
            onFragmentVisibleChange(true, true);
        } else if (!mIsFragmentVisible) {
            // 由可见——>不可见
            onFragmentVisibleChange(false, mIsFirstInit);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(NAME);
            mUrl = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_panda_live_sub, container, false);

            mBinding.videoView.getTitleTextView().setVisibility(View.GONE);
            mBinding.videoView.getBackButton().setVisibility(View.GONE);
            mBinding.videoView.getFullscreenButton().setVisibility(View.GONE);
            mBinding.videoView.findViewById(R.id.bottom_progressbar).setAlpha(0);
            mBinding.videoView.setIsTouchWiget(true);

            mRootView = mBinding.getRoot();
        }

        //可见，但是并没有加载过
        if (mIsFragmentVisible && !mIsFirstInit) {
            onFragmentVisibleChange(true, false);
        }

        return mRootView;
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
            mIsFirstInit = true;

            PandaLiveSubViewModel pandaLiveSubViewModel = ViewModelProviders.of(this, mFactory).get(PandaLiveSubViewModel.class);

            pandaLiveSubViewModel.getLiveTab(mUrl).observe(this, liveTabResource -> {
                if (liveTabResource != null) {
                    switch (liveTabResource.status) {
                        case SUCCESS:
                            LiveTab liveTab = liveTabResource.data;
                            if (liveTab != null && CommonUtil.isNotEmpty(liveTab.live)) {
                                VideoViewModel videoViewModel = ViewModelProviders.of(this, mFactory).get(VideoViewModel.class);
                                LiveTab.LiveBean liveBean = liveTab.live.get(0);
                                videoViewModel.getLiveVideo(liveBean.id).observe(PandaLiveSubFragment.this, liveVideoResource -> {
                                    if (liveVideoResource != null) {
                                        switch (liveVideoResource.status) {
                                            case SUCCESS:
                                                Log.e("PandaLiveSubFragment","145行-onFragmentVisibleChange(): "+" ");
                                                LiveVideo liveVideo = liveVideoResource.data;
                                                if (liveVideo != null) {
                                                    mBinding.videoView.setUp(liveVideo.hls_url.hls1, false, liveBean.title);
                                                    mBinding.videoView.startPlayLogic();
                                                }
                                                break;
                                            case ERROR:

                                                break;
                                            case LOADING:

                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                            }

                            break;
                        case ERROR:

                            break;
                        case LOADING:

                            break;
                        default:
                            break;
                    }
                }
            });

        } else if (isVisible && isFirstInit) {
            Log.e("PandaLiveSubFragment", mName + " 可见并且初始化过，不做网络请求");
        } else if (!isVisible) {
            Log.e("PandaLiveSubFragment", mName + "不可见不做网络请求 初始化了吗？" + isFirstInit);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TextView textView = getView().findViewById(R.id.tv_title);
        // textView.setText(mName + ";" + mUrl);
        Log.e("PandaLiveSubFragment", "72行-onActivityCreated(): " + mName + ";" + mUrl);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBinding.videoView.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.videoView.onVideoResume();
    }



}
