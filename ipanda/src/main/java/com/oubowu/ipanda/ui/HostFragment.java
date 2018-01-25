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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.databinding.FragmentHostBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FragmentDataBindingComponent;
import com.oubowu.ipanda.ui.adapter.HostAdapter;
import com.oubowu.ipanda.util.BarBehavior;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.viewmodel.HostViewModel;
import com.oubowu.stickyitemdecoration.StickyHeadContainer;
import com.oubowu.stickyitemdecoration.StickyItemDecoration;
import com.oushangfeng.pinnedsectionitemdecoration.SmallPinnedHeaderItemDecoration;

import javax.inject.Inject;


public class HostFragment extends Fragment implements Injectable {

    private static final String NAME = "name";
    private static final String URL = "url";

    private String mName;
    private String mUrl;

    private OnFragmentInteractionListener mListener;

    private FragmentHostBinding mBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    private Context mContext;

    private HostAdapter mHostAdapter;


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


        RecyclerView recyclerView = mBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        SmallPinnedHeaderItemDecoration itemDecoration = new SmallPinnedHeaderItemDecoration.Builder(R.id.header_tag, HostAdapter.TYPE_PANDA_NEWS,
                HostAdapter.TYPE_VIDEO_GRID, HostAdapter.TYPE_VIDEO_LIST).setDividerId(R.drawable.divider_host_fragment).enableDivider(true).create();
        itemDecoration.disableDrawHeader(true);
        recyclerView.addItemDecoration(itemDecoration);

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
        recyclerView.addItemDecoration(new StickyItemDecoration(container, HostAdapter.TYPE_PANDA_NEWS, HostAdapter.TYPE_VIDEO_GRID));

        mHostAdapter = new HostAdapter(new FragmentDataBindingComponent(this));
        recyclerView.setAdapter(mHostAdapter);

        HostViewModel hostViewModel = ViewModelProviders.of(this, mFactory).get(HostViewModel.class);

        hostViewModel.getHomeIndex(mUrl).observe(this, homeIndexResource -> {
            if (homeIndexResource != null) {
                switch (homeIndexResource.status) {
                    case SUCCESS:
                        // Logger.d(homeIndexResource.data);
                        if (homeIndexResource.data != null) {
                            mBinding.carouselViewPager.setList(homeIndexResource.data.bigImg);
                            mHostAdapter.replace(homeIndexResource.data);

                            if (CommonUtil.isNotEmpty(homeIndexResource.data.cctv.listurl)) {
                                hostViewModel.getWonderfulMomentIndex(homeIndexResource.data.cctv.listurl).observe(HostFragment.this, listResource -> {
                                    if (listResource != null) {
                                        switch (listResource.status) {
                                            case ERROR:
                                                break;
                                            case LOADING:
                                                break;
                                            case SUCCESS:
                                                homeIndexResource.data.cctv.list = listResource.data;
                                                mHostAdapter.notifyItemInserted(3);
                                                break;
                                        }
                                    }
                                });
                            }

                            if (CommonUtil.isNotEmpty(homeIndexResource.data.list)) {
                                HomeIndex.ListBeanXX listBean = homeIndexResource.data.list.get(0);
                                if (CommonUtil.isNotEmpty(listBean.listUrl)) {
                                    hostViewModel.getGungunVideoIndex(listBean.listUrl).observe(HostFragment.this, listResource -> {
                                        if (listResource != null) {
                                            switch (listResource.status) {
                                                case ERROR:
                                                    break;
                                                case LOADING:
                                                    break;
                                                case SUCCESS:
                                                    listBean.list = listResource.data;
                                                    mHostAdapter.notifyItemInserted(4);
                                                    break;
                                            }
                                        }
                                    });
                                }
                            }

                        }
                        break;
                    case ERROR:
                        Toast.makeText(getActivity(), "请求失败" + homeIndexResource.message, Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        Toast.makeText(getActivity(), "加载中......", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) mBinding.toolbar.getLayoutParams();
        CoordinatorLayout.Behavior behavior = clp.getBehavior();
        if (behavior != null && behavior instanceof BarBehavior) {
            ((BarBehavior) behavior).setOnNestedScrollListener((dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type) -> {
                if (mListener != null) {
                    mListener.onNestedScrollListener(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
                }
            });
        }

    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onButtonPressed();
        }
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

    public interface OnFragmentInteractionListener {
        void onNestedScrollListener(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type);

        void onButtonPressed();
    }
}
