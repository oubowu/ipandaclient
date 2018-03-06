package com.oubowu.ipanda.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.LazyFragment;
import com.oubowu.ipanda.callback.HandleBackInterface;
import com.oubowu.ipanda.callback.OnFragmentScrollListener;
import com.oubowu.ipanda.databinding.FragmentChinaLiveSubBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.ChinaLiveSubAdapter;
import com.oubowu.ipanda.ui.adapter.FragmentDataBindingComponent;
import com.oubowu.ipanda.viewmodel.ChinaLiveSubViewModel;
import com.oubowu.ipanda.viewmodel.VideoViewModel;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import javax.inject.Inject;

public class ChinaLiveSubFragment extends LazyFragment implements Injectable, HandleBackInterface {

    private static final String NAME = "name";
    private static final String URL = "url";
    private static final String PADDING_TOP = "paddingTop";

    private String mName;
    private String mUrl;
    private int mPaddingTop;

    private OnFragmentScrollListener mListener;

    private FragmentChinaLiveSubBinding mBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    private Context mContext;
    private ChinaLiveSubAdapter mAdapter;

    private boolean mIsPause;

    public ChinaLiveSubFragment() {
    }

    public static ChinaLiveSubFragment newInstance(String name, String url, int paddingTop) {
        ChinaLiveSubFragment fragment = new ChinaLiveSubFragment();
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
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.fragment_china_live_sub, container, false);

        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            Drawable drawable = ContextCompat.getDrawable(container.getContext(), R.drawable.divider_panda_live_fragment);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.top = mPaddingTop + drawable.getIntrinsicHeight();
                    outRect.left = drawable.getIntrinsicHeight();
                    outRect.right = drawable.getIntrinsicHeight();
                } else {
                    outRect.top = drawable.getIntrinsicHeight();
                    outRect.left = drawable.getIntrinsicHeight();
                    outRect.right = drawable.getIntrinsicHeight();
                    if (position == parent.getAdapter().getItemCount() - 1) {
                        outRect.bottom = drawable.getIntrinsicHeight();
                    }
                }
            }
        });

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));

        return mBinding.getRoot();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible, boolean isFirstInit) {
        if (isVisible && !isFirstInit) {

            ChinaLiveSubViewModel chinaLiveSubViewModel = ViewModelProviders.of(this, mFactory).get(ChinaLiveSubViewModel.class);
            VideoViewModel videoViewModel = ViewModelProviders.of(this, mFactory).get(VideoViewModel.class);

            chinaLiveSubViewModel.getChinaLiveDetail(mUrl).observe(this, recordTabResource -> {
                if (recordTabResource != null) {
                    switch (recordTabResource.status) {
                        case SUCCESS:

                            mIsFirstInit = true;

                            mAdapter = new ChinaLiveSubAdapter(this, new FragmentDataBindingComponent(this), videoViewModel);

                            mBinding.recyclerView.setAdapter(mAdapter);

                            if (recordTabResource.data != null) {
                                mAdapter.replace(recordTabResource.data);
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
            Log.e("ChinaLiveSubFragment", mName + " 可见并且初始化过，不做网络请求");
            GSYVideoManager.onResume();

            OrientationUtils orientationUtils = mAdapter.getOrientationUtils();
            if (orientationUtils != null) {
                orientationUtils.setEnable(mAdapter.hasPlayed());
            }

        } else if (!isVisible) {

            GSYVideoManager.onPause();

            OrientationUtils orientationUtils = mAdapter.getOrientationUtils();
            if (orientationUtils != null) {
                orientationUtils.setEnable(false);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentScrollListener) {
            mListener = (OnFragmentScrollListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        if (mAdapter != null) {
            mAdapter.onDestroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mIsPause = true;

        GSYVideoManager.onPause();

        if (mAdapter == null) {
            return;
        }

        OrientationUtils orientationUtils = mAdapter.getOrientationUtils();
        if (orientationUtils != null) {
            orientationUtils.setEnable(false);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mIsPause = false;

        if (!mIsFragmentVisible || (getParentFragment() != null && !getParentFragment().isVisible()) || mAdapter == null) {
            return;
        }

        GSYVideoManager.onResume();

        OrientationUtils orientationUtils = mAdapter.getOrientationUtils();
        if (orientationUtils != null) {
            orientationUtils.setEnable(mAdapter.hasPlayed());
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (mBinding == null || mAdapter == null) {
            return;
        }
        if (!mIsFragmentVisible) {
            return;
        }
        if (hidden) {
            GSYVideoManager.onPause();

            OrientationUtils orientationUtils = mAdapter.getOrientationUtils();
            if (orientationUtils != null) {
                orientationUtils.setEnable(false);
            }

        } else {
            GSYVideoManager.onResume();

            OrientationUtils orientationUtils = mAdapter.getOrientationUtils();
            if (orientationUtils != null) {
                orientationUtils.setEnable(mAdapter.hasPlayed());
            }

        }
    }

    private void onBackPressAdapter() {
        //为了支持重力旋转
        if (mAdapter != null && mAdapter.getListNeedAutoLand()) {
            mAdapter.onBackPressed();
        }
    }

    /********************************为了支持重力旋转********************************/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("ChinaLiveSubFragment", "202行-onConfigurationChanged(): " + " ");
        if (mAdapter != null && mAdapter.getListNeedAutoLand() && !mIsPause) {
            mAdapter.onConfigurationChanged(getActivity(), newConfig);
        }
    }


    @Override
    public boolean onBackPressed() {
        //为了支持重力旋转
        onBackPressAdapter();

        return StandardGSYVideoPlayer.backFromWindowFull(getActivity());
    }
}
