package com.oubowu.ipanda.ui.adapter;

import android.databinding.DataBindingComponent;
import android.support.v4.app.Fragment;

/**
 * Created by Oubowu on 2018/1/15 16:49.
 */
public class FragmentDataBindingComponent implements DataBindingComponent {

    private final FragmentBindingAdapters mFragmentBindingAdapters;

    public FragmentDataBindingComponent(Fragment fragment) {
        mFragmentBindingAdapters = new FragmentBindingAdapters(fragment);
    }

    @Override
    public BindingAdapters getBindingAdapters() {
        return null;
    }

    @Override
    public FragmentBindingAdapters getFragmentBindingAdapters() {
        return mFragmentBindingAdapters;
    }

}
