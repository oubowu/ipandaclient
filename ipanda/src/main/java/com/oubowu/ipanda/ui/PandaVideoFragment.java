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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.pandavideo.PandaVideoIndex;
import com.oubowu.ipanda.callback.OnFragmentScrollListener;
import com.oubowu.ipanda.databinding.FragmentPandaVideoBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FragmentDataBindingComponent;
import com.oubowu.ipanda.ui.adapter.PandaVideoAdapter;
import com.oubowu.ipanda.util.BarBehavior;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.viewmodel.PandaVideoViewModel;

import javax.inject.Inject;

public class PandaVideoFragment extends Fragment implements Injectable, SwipeRefreshLayout.OnRefreshListener {

    private static final String NAME = "name";
    private static final String URL = "url";

    private String mName;
    private String mUrl;

    private OnFragmentScrollListener mScrollListener;

    private FragmentPandaVideoBinding mBinding;
    private PandaVideoAdapter mPandaVideoAdapter;
    private Context mContext;

    @Inject
    ViewModelProvider.Factory mFactory;

    private PandaVideoViewModel mPandaVideoViewModel;

    public PandaVideoFragment() {
    }

    public static PandaVideoFragment newInstance(String name, String url) {
        PandaVideoFragment fragment = new PandaVideoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_panda_video, container, false, new FragmentDataBindingComponent(this));

        mBinding.setTitle(mName);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dispatchScrollEvent();

        initSwipeRefreshLayout();

        initRecyclerView();

        getPandaVideoIndex();

    }

    private void initSwipeRefreshLayout() {
        mBinding.toolbar.post(() -> {
            mBinding.swipeRefreshLayout.setProgressViewOffset(false, mBinding.toolbar.getBottom(), (int) (mBinding.toolbar.getBottom() * 1.5));
            mBinding.swipeRefreshLayout.setRefreshing(true);
            mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        });
    }

    private void getPandaVideoIndex() {
        if (mPandaVideoViewModel == null) {
            mPandaVideoViewModel = ViewModelProviders.of(this, mFactory).get(PandaVideoViewModel.class);
        }

        mPandaVideoViewModel.getPandaVideoIndex(mUrl).observe(this, new ObserverImpl<PandaVideoIndex>() {
            @Override
            protected void onSuccess(@NonNull PandaVideoIndex data) {
                if (CommonUtil.isNotEmpty(data.bigImg)) {
                    PandaVideoIndex.BigImgBean bigImgBean = data.bigImg.get(0);
                    PandaVideoIndex.ListBean listBean = new PandaVideoIndex.ListBean();
                    listBean.title = bigImgBean.title;
                    listBean.image = bigImgBean.image;
                    listBean.id = bigImgBean.pid;
                    listBean.url = bigImgBean.url;
                    listBean.type = bigImgBean.type;
                    data.list.add(0, listBean);
                }
                mPandaVideoAdapter.replace(data.list);

                mBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mPandaVideoAdapter = new PandaVideoAdapter(new FragmentDataBindingComponent(this));

        mBinding.recyclerView.setAdapter(mPandaVideoAdapter);

        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.divider_panda_live_fragment);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.top = mBinding.toolbar.getHeight();
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
    }

    private void dispatchScrollEvent() {
        CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) mBinding.toolbar.getLayoutParams();
        CoordinatorLayout.Behavior behavior = clp.getBehavior();
        if (behavior != null && behavior instanceof BarBehavior) {
            ((BarBehavior) behavior).setOnNestedScrollListener(new BarBehavior.OnNestedScrollListener() {
                @Override
                public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
                    mScrollListener.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
                }

                @Override
                public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
                    return mScrollListener.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentScrollListener) {
            mScrollListener = (OnFragmentScrollListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        mScrollListener = null;
    }

    @Override
    public void onRefresh() {
        getPandaVideoIndex();
    }

}
