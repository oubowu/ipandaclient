package com.oubowu.ipanda.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.databinding.FragmentHostBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FragmentDataBindingComponent;
import com.oubowu.ipanda.ui.adapter.HostAdapter;
import com.oubowu.ipanda.viewmodel.HostViewModel;

import java.util.Arrays;

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

        HostViewModel hostViewModel = ViewModelProviders.of(this, mFactory).get(HostViewModel.class);

        hostViewModel.getHomeIndex(mUrl).observe(this, homeIndexResource -> {
            if (homeIndexResource != null) {
                switch (homeIndexResource.status) {
                    case SUCCESS:
                        // Logger.d(homeIndexResource.data);
                        if (homeIndexResource.data != null) {
                            mBinding.carouselViewPager.setList(homeIndexResource.data.bigImg);
                        }
                        break;
                    case ERROR:
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        Toast.makeText(getActivity(), "加载中......", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        HostAdapter hostAdapter = new HostAdapter(new FragmentDataBindingComponent(this));
        mBinding.recyclerView.setAdapter(hostAdapter);
        hostAdapter.replace(
                Arrays.asList("1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111",
                        "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111",
                        "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111", "1111"));

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
