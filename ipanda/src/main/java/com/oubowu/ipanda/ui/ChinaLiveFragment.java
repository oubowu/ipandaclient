package com.oubowu.ipanda.ui;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.chinalive.ChinaLiveTab;
import com.oubowu.ipanda.callback.HandleBackInterface;
import com.oubowu.ipanda.callback.OnFragmentScrollListener;
import com.oubowu.ipanda.databinding.FragmentChinaLiveBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FragmentAdapter;
import com.oubowu.ipanda.util.BarBehavior;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.HandleBackUtil;
import com.oubowu.ipanda.viewmodel.ChinaLiveViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChinaLiveFragment extends Fragment implements Injectable,HandleBackInterface {

    private static final String NAME = "name";
    private static final String URL = "url";

    private String mName;
    private String mUrl;
    private Context mContext;
    private FragmentChinaLiveBinding mBinding;

    private OnFragmentScrollListener mListener;

    @Inject
    ViewModelProvider.Factory mFactory;
    private FragmentAdapter mPandaLiveFragmentAdapter;

    public ChinaLiveFragment() {
        // Required empty public constructor
    }

    public static ChinaLiveFragment newInstance(String name, String url) {
        ChinaLiveFragment fragment = new ChinaLiveFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_china_live, container, false);
        mBinding.setTitle(mName);

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

        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mBinding.coordinatorLayout.onNestedScroll(mBinding.viewPager, 0, -10000, 0, 0);
                        break;
                }
            }
        });

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ChinaLiveViewModel chinaLiveViewModel = ViewModelProviders.of(this, mFactory).get(ChinaLiveViewModel.class);
        chinaLiveViewModel.getChinaLiveTab(mUrl).observe(this, new ObserverImpl<ChinaLiveTab>() {
            @Override
            protected void onSuccess(@NonNull ChinaLiveTab data) {
                if (CommonUtil.isNotEmpty(data.tablist)) {
                    List<Fragment> fragments = new ArrayList<>(data.tablist.size());
                    List<String> titles = new ArrayList<>(data.tablist.size());
                    int paddingTop = mBinding.toolbar.getHeight() + mBinding.tabLayout.getHeight();
                    for (ChinaLiveTab.TablistBean tab : data.tablist) {
                        titles.add(tab.title);
                        fragments.add(ChinaLiveSubFragment.newInstance(tab.title, tab.url, paddingTop));
                    }
                    mPandaLiveFragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, titles);
                    mBinding.viewPager.setAdapter(mPandaLiveFragmentAdapter);
                    mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
                }
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
    public void onHiddenChanged(boolean hidden) {
        if (mPandaLiveFragmentAdapter != null) {
            List<Fragment> fragments = mPandaLiveFragmentAdapter.getFragments();
            for (Fragment f : fragments) {
                f.onHiddenChanged(hidden);
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        return HandleBackUtil.handleBackPress(this);
    }
}
