package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.databinding.DataBindingComponent;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.bean.pandalive.RecordTab;
import com.oubowu.ipanda.callback.OnItemClickCallback;
import com.oubowu.ipanda.databinding.ItemFragmentPandaliveOtherBinding;
import com.oubowu.ipanda.util.CommonUtil;

/**
 * Created by Oubowu on 2018/2/23 16:54.
 */
public class PandaLiveOtherAdapter extends DataBoundListAdapter<RecordTab.VideoBean, ItemFragmentPandaliveOtherBinding> implements View.OnClickListener {

    private DataBindingComponent mDataBindingComponent;

    private OnItemClickCallback mClickCallback;

    public void setClickCallback(OnItemClickCallback clickCallback) {
        mClickCallback = clickCallback;
    }

    public PandaLiveOtherAdapter(android.databinding.DataBindingComponent dataBindingComponent) {
        mDataBindingComponent = dataBindingComponent;
    }

    @Override
    protected ItemFragmentPandaliveOtherBinding createBinding(ViewGroup parent, int viewType) {
        ItemFragmentPandaliveOtherBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_pandalive_other, parent, false, mDataBindingComponent);
        return binding;
    }

    @Override
    protected void bind(ItemFragmentPandaliveOtherBinding binding, int position, RecordTab.VideoBean videoBean, int itemViewType) {

        binding.getRoot().setTag(-1, position);
        binding.getRoot().setOnClickListener(this);

        binding.setVideoBean(videoBean);
        binding.executePendingBindings();

    }

    @Override
    protected boolean areItemsTheSame(RecordTab.VideoBean oldItem, RecordTab.VideoBean newItem) {
        return CommonUtil.equals(oldItem.vid, newItem.vid);
    }

    @Override
    protected boolean areContentsTheSame(RecordTab.VideoBean oldItem, RecordTab.VideoBean newItem) {
        return CommonUtil.equals(oldItem.vid, newItem.vid);
    }

    @Override
    public void onClick(View v) {
        if (mClickCallback != null) {
            mClickCallback.onItemClick(v, (Integer) v.getTag(-1));
        }
    }
}
