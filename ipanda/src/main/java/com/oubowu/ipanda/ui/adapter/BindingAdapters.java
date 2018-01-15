package com.oubowu.ipanda.ui.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.ui.widget.DescImage;
import com.oubowu.ipanda.util.MeasureUtil;

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
            // Log.e("BindingAdapters", "26行-setToolbarTitle(): " + title);
        }
        //else {
        // Log.e("BindingAdapters", "27行-setToolbarTitle(): " + " ");
        //}
        toolbar.setTitle(title);
    }

    @BindingAdapter("loadDescImage")
    public static void loadImg(DescImage imageView, HomeIndex.BigImgBean bigImgBean) {
        imageView.setDesc(bigImgBean.title);
        Glide.with(imageView).load(bigImgBean.image).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int w = params.width = MeasureUtil.getScreenSize(imageView.getContext()).x;
                float scale = (float) w / (float) resource.getIntrinsicWidth();
                params.height = Math.round(resource.getIntrinsicHeight() * scale);
                imageView.setLayoutParams(params);
                return false;
            }
        }).into(imageView);
    }

}
