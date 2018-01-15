package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.databinding.ItemFragmentHostBinding;

/**
 * Created by Oubowu on 2018/1/15 15:56.
 */
public class HostAdapter extends DataBoundListAdapter<String, ItemFragmentHostBinding> {

    private DataBindingComponent mDataBindingComponent;

    public HostAdapter(DataBindingComponent dataBindingComponent) {
        mDataBindingComponent = dataBindingComponent;
    }

    @Override
    protected ItemFragmentHostBinding createBinding(ViewGroup parent) {
        ItemFragmentHostBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fragment_host, parent, false, mDataBindingComponent);
        return binding;
    }

    @Override
    protected void bind(ItemFragmentHostBinding binding, String s) {
        binding.setName(s);
    }

    @Override
    protected boolean areItemsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }

    @Override
    protected boolean areContentsTheSame(String oldItem, String newItem) {
        return oldItem.equals(newItem);
    }
}
