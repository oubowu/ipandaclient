package com.example.ipanda1.ui.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by Oubowu on 2017/12/13 16:38.
 */
public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
