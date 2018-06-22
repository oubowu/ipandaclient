package com.oubowu.ipanda.ui.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.oubowu.ipanda.bean.pandabroadcast.PandaBroadcastList;
import com.oubowu.ipanda.bean.pandavideo.PandaVideoIndex;
import com.oubowu.ipanda.ui.widget.DescImageView;
import com.oubowu.ipanda.util.GlideConfig;
import com.oubowu.ipanda.util.MeasureUtil;

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
        Glide.with(mFragment).load(url).apply(GlideConfig.getInstance()).transition(GlideConfig.getTransitionOptions()).into(imageView);
    }

    @BindingAdapter("loadDescImage1")
    public static void loadImg(DescImageView imageView, PandaVideoIndex.ListBean bean) {
        imageView.setDesc(bean.title);
        Glide.with(imageView).load(bean.image).apply(GlideConfig.getInstance()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int w = params.width = MeasureUtil.getScreenSize(imageView.getContext()).x;
                float scale = (float) w / (float) resource.getIntrinsicWidth();
                params.height = Math.round(resource.getIntrinsicHeight() * scale);
                imageView.setLayoutParams(params);
                return false;
            }
        }).transition(GlideConfig.getTransitionOptions()).into(imageView);
    }

    @BindingAdapter("loadDescImage2")
    public static void loadImg2(DescImageView imageView, PandaBroadcastList.ListBean bean) {
        imageView.setDesc(bean.title);
        Glide.with(imageView).load(bean.picurl).apply(GlideConfig.getInstance()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int w = params.width = MeasureUtil.getScreenSize(imageView.getContext()).x;
                float scale = (float) w / (float) resource.getIntrinsicWidth();
                params.height = Math.round(resource.getIntrinsicHeight() * scale);
                imageView.setLayoutParams(params);
                return false;
            }
        }).transition(GlideConfig.getTransitionOptions()).into(imageView);
    }

}
