package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingComponent;

/**
 * Created by Oubowu on 2018/1/15 16:49.
 */
public class ActivityDataBindingComponent implements DataBindingComponent {

    private final BindingAdapters mBindingAdapters;

    public ActivityDataBindingComponent() {
        mBindingAdapters = new BindingAdapters();
    }

    @Override
    public BindingAdapters getBindingAdapters() {
        return mBindingAdapters;
    }

    @Override
    public FragmentBindingAdapters getFragmentBindingAdapters() {
        return null;
    }

}
