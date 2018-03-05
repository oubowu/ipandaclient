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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastIndex;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastList;
import com.oubowu.ipanda.callback.OnFragmentInteractionListener;
import com.oubowu.ipanda.databinding.FragmentPandaBroadcastBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FragmentDataBindingComponent;
import com.oubowu.ipanda.ui.adapter.PandaBroadcastAdapter;
import com.oubowu.ipanda.util.BarBehavior;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.viewmodel.PandaBroadcastViewModel;

import java.util.List;

import javax.inject.Inject;

public class PandaBroadcastFragment extends Fragment implements Injectable {

    private static final String NAME = "name";
    private static final String URL = "url";

    private String mName;
    private String mUrl;

    private OnFragmentInteractionListener mListener;
    private FragmentPandaBroadcastBinding mBinding;

    private Context mContext;

    @Inject
    ViewModelProvider.Factory mFactory;
    private PandaBroadcastAdapter mPandaBroadcastAdapter;

    public PandaBroadcastFragment() {
    }

    public static PandaBroadcastFragment newInstance(String name, String url) {
        PandaBroadcastFragment fragment = new PandaBroadcastFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_panda_broadcast, container, false, new FragmentDataBindingComponent(this));

        CoordinatorLayout.LayoutParams clp = (CoordinatorLayout.LayoutParams) mBinding.toolbar.getLayoutParams();
        CoordinatorLayout.Behavior behavior = clp.getBehavior();
        if (behavior != null && behavior instanceof BarBehavior) {
            ((BarBehavior) behavior).setOnNestedScrollListener((dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type) -> {
                if (mListener != null) {
                    mListener.onNestedScrollListener(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
                }
            });
        }

        mBinding.setTitle(mName);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mPandaBroadcastAdapter = new PandaBroadcastAdapter(new FragmentDataBindingComponent(this));

        mBinding.recyclerView.setAdapter(mPandaBroadcastAdapter);

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

        PandaBroadcastViewModel pandaBroadcastViewModel = ViewModelProviders.of(this, mFactory).get(PandaBroadcastViewModel.class);

        pandaBroadcastViewModel.getPandaBroadcastIndex(mUrl).observe(this, pandaBroadcastIndexResource -> {
            if (pandaBroadcastIndexResource != null) {
                switch (pandaBroadcastIndexResource.status) {
                    case SUCCESS:

                        PandaBroadcastIndex data = pandaBroadcastIndexResource.data;

                        if (data != null) {
                            List<PandaBroadcastIndex.BigImgBean> bigImg = data.bigImg;

                            pandaBroadcastViewModel.getPandaBroadcastList(data.listurl).observe(PandaBroadcastFragment.this, pandaBroadcastListResource -> {
                                if (pandaBroadcastListResource != null) {
                                    switch (pandaBroadcastListResource.status) {
                                        case SUCCESS:

                                            PandaBroadcastList broadcastList = pandaBroadcastListResource.data;

                                            if (broadcastList != null) {

                                                if (CommonUtil.isNotEmpty(broadcastList.list)) {

                                                    if (CommonUtil.isNotEmpty(bigImg)) {
                                                        PandaBroadcastIndex.BigImgBean bigImgBean = bigImg.get(0);
                                                        PandaBroadcastList.ListBean item = new PandaBroadcastList.ListBean();
                                                        item.id = bigImgBean.pid;
                                                        item.picurl = bigImgBean.image;
                                                        item.title = bigImgBean.title;
                                                        item.url = bigImgBean.url;
                                                        item.datatype = bigImgBean.type;
                                                        broadcastList.list.add(0, item);
                                                    }

                                                    mPandaBroadcastAdapter.replace(broadcastList.list);

                                                }

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
                    case LOADING:

                        break;
                    case ERROR:

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
        mContext = null;
        mListener = null;
    }

}
