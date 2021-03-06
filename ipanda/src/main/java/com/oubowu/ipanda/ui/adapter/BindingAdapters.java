package com.oubowu.ipanda.ui.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.oubowu.ipanda.bean.home.HomeIndex;
import com.oubowu.ipanda.ui.widget.DescImageView;
import com.oubowu.ipanda.util.GlideConfig;

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
        }
        toolbar.setTitle(title);
    }

    @BindingAdapter("loadSimpleVideoImageUrl")
    public void bindImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).apply(GlideConfig.getInstance()).transition(GlideConfig.getTransitionOptions()).into(imageView);
    }

    @BindingAdapter("loadDescImage")
    public static void loadImg(DescImageView imageView, HomeIndex.BigImgBean bigImgBean) {
        imageView.setDesc(bigImgBean.title);
        Glide.with(imageView).load(bigImgBean.image).apply(GlideConfig.getInstance())/*.listener(new RequestListener<Drawable>() {
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
        })*/.transition(GlideConfig.getTransitionOptions()).into(imageView);
    }

}
