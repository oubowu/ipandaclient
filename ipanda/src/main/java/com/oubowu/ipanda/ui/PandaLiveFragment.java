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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandalive.TabList;
import com.oubowu.ipanda.callback.OnFragmentInteractionListener;
import com.oubowu.ipanda.databinding.FragmentPandaLiveBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.PandaLiveFragmentAdapter;
import com.oubowu.ipanda.util.BarBehavior;
import com.oubowu.ipanda.viewmodel.PandaLiveViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PandaLiveFragment extends Fragment implements Injectable {

    private static final String NAME = "name";
    private static final String URL = "url";

    private String mName;
    private String mUrl;
    private Context mContext;
    private FragmentPandaLiveBinding mBinding;

    private OnFragmentInteractionListener mListener;

    @Inject
    ViewModelProvider.Factory mFactory;
    private PandaLiveFragmentAdapter mPandaLiveFragmentAdapter;

    public PandaLiveFragment() {
        // Required empty public constructor
    }

    public static PandaLiveFragment newInstance(String name, String url) {
        PandaLiveFragment fragment = new PandaLiveFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_panda_live, container, false);
        mBinding.setTitle(mName);

        CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) mBinding.toolbar.getLayoutParams();
        CoordinatorLayout.Behavior behavior = clp.getBehavior();
        if (behavior != null && behavior instanceof BarBehavior) {
            ((BarBehavior) behavior).setOnNestedScrollListener((dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type) -> {
                if (mListener != null) {
                    mListener.onNestedScrollListener(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
                }
            });
        }

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PandaLiveViewModel pandaLiveViewModel = ViewModelProviders.of(this, mFactory).get(PandaLiveViewModel.class);

        pandaLiveViewModel.getTabList(mUrl).observe(this, listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case SUCCESS:
                        List<TabList> data = listResource.data;
                        if (data != null) {
                            List<Fragment> fragments = new ArrayList<>(data.size());
                            List<String> titles = new ArrayList<>(data.size());
                            for (TabList tab : data) {
                                titles.add(tab.title);
                                fragments.add(PandaLiveSubFragment.newInstance(tab.title, tab.url, mBinding.toolbar.getHeight() + mBinding.tabLayout.getHeight()));
                            }
                            Log.e("PandaLiveFragment", "89行-onActivityCreated(): " + " ");
                            mPandaLiveFragmentAdapter = new PandaLiveFragmentAdapter(getChildFragmentManager(), fragments, titles);
                            mBinding.viewPager.setAdapter(mPandaLiveFragmentAdapter);
                            mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
            for (Fragment f:fragments) {
                f.onHiddenChanged(hidden);
            }
        }
    }
}