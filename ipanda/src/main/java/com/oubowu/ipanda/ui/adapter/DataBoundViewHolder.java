package com.oubowu.ipanda.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Oubowu on 2018/1/15 15:58.
 */
public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final T binding;

    public DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
