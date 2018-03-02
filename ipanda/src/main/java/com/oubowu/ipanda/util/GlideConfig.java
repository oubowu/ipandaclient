package com.oubowu.ipanda.util;

import com.bumptech.glide.request.RequestOptions;
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
                    REQUEST_OPTIONS = new RequestOptions().placeholder(R.drawable.xxx).error(R.drawable.xxx);
                }
            }
        }
        return REQUEST_OPTIONS;
    }

}
