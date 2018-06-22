package com.oubowu.ipanda.util;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.oubowu.ipanda.R;

/**
 * Created by Oubowu on 2018/2/26 14:17.
 */
public class GlideConfig {

    private static volatile RequestOptions REQUEST_OPTIONS;

    private GlideConfig() {
    }

    public static RequestOptions getInstance() {
        if (REQUEST_OPTIONS == null) {
            synchronized (GlideConfig.class) {
                if (REQUEST_OPTIONS == null) {
                    REQUEST_OPTIONS = new RequestOptions().placeholder(R.drawable.ic_panda_loading).error(R.drawable.ic_loading_fail).fitCenter();
                }
            }
        }
        return REQUEST_OPTIONS;
    }

    public static DrawableTransitionOptions getTransitionOptions(){
        return DrawableTransitionOptions.withCrossFade(new DrawableCrossFadeFactory.Builder(250).setCrossFadeEnabled(true));
    }

}
