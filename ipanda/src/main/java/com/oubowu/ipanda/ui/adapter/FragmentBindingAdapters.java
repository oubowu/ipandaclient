package com.oubowu.ipanda.ui.adapter;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 * Created by Oubowu on 2018/1/15 16:50.
 */
public class FragmentBindingAdapters {

    private final Fragment mFragment;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        mFragment = fragment;
    }

    @BindingAdapter("loadImageUrl")
    public void bindImage(ImageView imageView, String url) {
        Glide.with(mFragment).load(url).into(imageView);
    }

}
