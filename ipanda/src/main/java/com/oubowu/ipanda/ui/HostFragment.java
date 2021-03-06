package com.oubowu.ipanda.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.base.VideoList;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.callback.OnFragmentScrollListener;
import com.oubowu.ipanda.databinding.FragmentHostBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FragmentDataBindingComponent;
import com.oubowu.ipanda.ui.adapter.HostAdapter;
import com.oubowu.ipanda.ui.widget.CarouselViewPager;
import com.oubowu.ipanda.util.BarBehavior;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.ToastUtil;
import com.oubowu.ipanda.viewmodel.HostViewModel;
import com.oubowu.stickyitemdecoration.StickyHeadContainer;
import com.oubowu.stickyitemdecoration.StickyItemDecoration;

import java.util.List;

import javax.inject.Inject;


public class HostFragment extends Fragment implements Injectable, SwipeRefreshLayout.OnRefreshListener {

    private static final String NAME = "name";
    private static final String URL = "url";

    private String mName;
    private String mUrl;

    private OnFragmentScrollListener mListener;

    private FragmentHostBinding mBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    private Context mContext;

    private HostAdapter mHostAdapter;

    private HostViewModel mHostViewModel;

    public HostFragment() {
        // Required empty public constructor
    }

    public static HostFragment newInstance(String name, String url) {
        HostFragment fragment = new HostFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_host, container, false);

        mBinding.setTitle(mName);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSwipeRefreshLayout();

        initRecyclerView();

        controlSwipeRefreshLayout();

        initStickyHeader();

        getHomeIndex();

        dispatchScrollEvent();

    }

    private void dispatchScrollEvent() {
        CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) mBinding.toolbar.getLayoutParams();
        CoordinatorLayout.Behavior behavior = clp.getBehavior();
        if (behavior != null && behavior instanceof BarBehavior) {
            ((BarBehavior) behavior).setOnNestedScrollListener(new BarBehavior.OnNestedScrollListener() {
                @Override
                public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
                    mListener.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
                }

                @Override
                public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
                    return mListener.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
                }
            });
        }
    }

    private void initStickyHeader() {
        final StickyHeadContainer container = mBinding.stickyHeadContainer;
        container.setDataCallback(pos -> {
            HomeIndex homeIndex = mHostAdapter.getHomeIndex();
            switch (pos) {
                case 0:
                    mBinding.setStickyTitle(homeIndex.pandaeye.title);
                    break;
                case 1:
                    mBinding.setStickyTitle(homeIndex.pandalive.title);
                    break;
                case 2:
                    mBinding.setStickyTitle(homeIndex.chinalive.title);
                    break;
                case 3:
                    mBinding.setStickyTitle(homeIndex.cctv.title);
                    break;
                case 4:
                    mBinding.setStickyTitle(homeIndex.list.get(0).title);
                    break;
                default:
                    break;
            }
        });
        mBinding.recyclerView.addItemDecoration(new StickyItemDecoration(container, HostAdapter.TYPE_PANDA_NEWS, HostAdapter.TYPE_VIDEO_GRID));
    }

    private void controlSwipeRefreshLayout() {
        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // int topRowVerticalPosition = recyclerView.getChildCount() == 0 ? 0 : recyclerView.getChildAt(0).getTop();
                float topRowVerticalPosition = mBinding.carouselViewPager.getY();
                mBinding.swipeRefreshLayout.setEnabled(topRowVerticalPosition >= mBinding.toolbar.getHeight());
            }
        });

        mBinding.carouselViewPager.setPageChangeListener(new CarouselViewPager.OnPageChangeListenerAdapter() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mBinding.swipeRefreshLayout.setEnabled(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mBinding.swipeRefreshLayout.getChildAt(mBinding.swipeRefreshLayout.getChildCount() - 1).getVisibility() == View.VISIBLE) {
                    return;
                }
                if ((mBinding.carouselViewPager.getY() < mBinding.toolbar.getHeight())) {
                    return;
                }
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mBinding.swipeRefreshLayout.setEnabled(true);
                } else {
                    mBinding.swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = mBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.divider_panda_live_fragment);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.top = 0;
                } else {
                    outRect.top = drawable.getIntrinsicHeight();
                    if (position == parent.getAdapter().getItemCount() - 1) {
                        outRect.bottom = drawable.getIntrinsicHeight();
                    }
                }
            }
        });
        mHostAdapter = new HostAdapter(new FragmentDataBindingComponent(this));
        recyclerView.setAdapter(mHostAdapter);
    }

    private void initSwipeRefreshLayout() {
        mBinding.toolbar.post(() -> {
            mBinding.swipeRefreshLayout.setProgressViewOffset(false, mBinding.toolbar.getBottom(), (int) (mBinding.toolbar.getBottom() * 1.5));
            mBinding.swipeRefreshLayout.setRefreshing(true);
            mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        });
    }

    private void getHomeIndex() {

        if (mHostViewModel == null) {
            mHostViewModel = ViewModelProviders.of(this, mFactory).get(HostViewModel.class);
        }

        mHostViewModel.getHomeIndex(mUrl).observe(this, new ObserverImpl<HomeIndex>() {
            @Override
            protected void onError(@NonNull String errorMsg) {
                ToastUtil.showSuccessMsg(errorMsg);
            }

            @Override
            protected void onLoading() {
                ToastUtil.showSuccessMsg("加载中......");
            }

            @Override
            protected void onSuccess(@NonNull HomeIndex data) {
                mBinding.carouselViewPager.setList(data.bigImg);
                mHostAdapter.replace(data);

                mBinding.swipeRefreshLayout.setRefreshing(false);

                if (CommonUtil.isNotEmpty(data.cctv.listurl)) {
                    getWonderfulMomentIndex(data, mHostViewModel);
                }

                if (CommonUtil.isNotEmpty(data.list)) {
                    getGungunVideoIndex(data, mHostViewModel);
                }

            }
        });
    }

    private void getGungunVideoIndex(@NonNull HomeIndex data, HostViewModel hostViewModel) {
        HomeIndex.ListBeanXX listBean = data.list.get(0);
        if (CommonUtil.isNotEmpty(listBean.listUrl)) {
            hostViewModel.getGungunVideoIndex(listBean.listUrl).observe(HostFragment.this, new ObserverImpl<List<VideoList>>() {
                @Override
                protected void onSuccess(@NonNull List<VideoList> data) {
                    listBean.list = data;
                    mHostAdapter.notifyItemInserted(4);
                }
            });
        }
    }

    private void getWonderfulMomentIndex(@NonNull HomeIndex homeIndex, HostViewModel hostViewModel) {
        hostViewModel.getWonderfulMomentIndex(homeIndex.cctv.listurl).observe(HostFragment.this, new ObserverImpl<List<VideoList>>() {
            @Override
            protected void onSuccess(@NonNull List<VideoList> data) {
                homeIndex.cctv.list = data;
                mHostAdapter.notifyItemInserted(3);
            }
        });
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
        mListener = null;
        // 取消所有异步任务
        mContext = null;
    }

    @Override
    public void onRefresh() {
        getHomeIndex();
    }
}
