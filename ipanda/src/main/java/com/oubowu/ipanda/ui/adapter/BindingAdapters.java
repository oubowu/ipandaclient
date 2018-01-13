package com.oubowu.ipanda.ui.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

/**
 * Created by Oubowu on 2017/12/13 16:38.
 */
public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("toolbarTitle")
    public static void setToolbarTitle(Toolbar toolbar, String title) {
        Context context = toolbar.getContext();
        if (context instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) context;
            activity.setSupportActionBar(toolbar);
            Log.e("BindingAdapters","26行-setToolbarTitle(): "+title);
        }else {
            Log.e("BindingAdapters","27行-setToolbarTitle(): "+" ");
        }
        toolbar.setTitle(title);
    }

}
