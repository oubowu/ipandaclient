package com.oubowu.ipanda.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.base.LazyFragment;
import com.oubowu.ipanda.base.ObserverImpl;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.callback.OnFragmentScrollListener;
import com.oubowu.ipanda.databinding.FragmentPandaLiveOtherBinding;
import com.oubowu.ipanda.di.Injectable;
import com.oubowu.ipanda.ui.adapter.FragmentDataBindingComponent;
import com.oubowu.ipanda.ui.adapter.PandaLiveOtherAdapter;
import com.oubowu.ipanda.viewmodel.PandaLiveOtherViewModel;

import javax.inject.Inject;

public class PandaLiveOtherFragment extends LazyFragment implements Injectable {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String PADDING_TOP = "paddingTop";

    private String mName;
    private String mId;
    private int mPaddingTop;

    private OnFragmentScrollListener mListener;

    private FragmentPandaLiveOtherBinding mBinding;

    @Inject
    ViewModelProvider.Factory mFactory;

    private Context mContext;

    public PandaLiveOtherFragment() {
    }

    public static PandaLiveOtherFragment newInstance(String name, String vsId, int paddingTop) {
        PandaLiveOtherFragment fragment = new PandaLiveOtherFragment();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(ID, vsId);
        args.putInt(PADDING_TOP, paddingTop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(NAME);
            mId = getArguments().getString(ID);
            mPaddingTop = getArguments().getInt(PADDING_TOP);
        }
    }

    @Override
    protected @NonNull
    View inflateView(LayoutInflater inflater, ViewGroup container) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.fragment_panda_live_other, container, false);

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

            PandaLiveOtherViewModel pandaLiveOtherViewModel = ViewModelProviders.of(this, mFactory).get(PandaLiveOtherViewModel.class);

            pandaLiveOtherViewModel.getRecordTab(mId, 10, 0).observe(this, new ObserverImpl<RecordTab>() {
                @Override
                protected void onSuccess(@NonNull RecordTab data) {
                    mIsFirstInit = true;

                    PandaLiveOtherAdapter adapter = new PandaLiveOtherAdapter(new FragmentDataBindingComponent(PandaLiveOtherFragment.this));

                    mBinding.recyclerView.setAdapter(adapter);

                    adapter.setClickCallback((view, position) -> {
                        VideoActivity.start(getActivity(), view, adapter.getItem(position).vid);
                    });

                    adapter.replace(data.video);
                }
            });

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
}
